package org.skyscreamer.yoga.mapper;

import static org.skyscreamer.yoga.util.ObjectUtil.isPrimitive;

import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;

public class ResultTraverser
{

    protected ClassFinderStrategy _classFinderStrategy = new DefaultClassFinderStrategy();

    public void traverse( Object instance, Selector selector, HierarchicalModel<?> model,
            YogaRequestContext context )
    {
        if (instance == null)
        {
            return;
        }

//        if (instance instanceof Map)
//        {
//            traverseMap( (Map<String, Object>) instance, selector, (MapHierarchicalModel<?>) model,
//                    context );
//        }
//        else 
        
        if (instance instanceof Iterable)
        {
            traverseIterable( (Iterable<?>) instance, selector, (ListHierarchicalModel<?>) model,
                    context );
        }
        else
        {
            traversePojo( instance, selector, (MapHierarchicalModel<?>) model, context );
        }
    }

    protected void traverseIterable( Iterable<?> instance, Selector selector,
            ListHierarchicalModel<?> model, YogaRequestContext context )
    {
        for (Object o : instance)
        {
            if (isPrimitive( o.getClass() ))
            {
                model.addValue( o );
            }
            else
            {
                traverse( o, selector, model.createChildMap(), context );
            }
        }
        context.emitEvent( new RenderingEvent( RenderingEventType.LIST_CHILD, model, instance,
                instance.getClass(), context, selector ) );
    }

    /* TODO: Figure this one out... all of our selectors are pojo based 
    protected void traverseMap( Map<String, Object> map, Selector selector,
            MapHierarchicalModel<?> model, YogaRequestContext context )
    {
        for (Entry<String, Object> entry : map.entrySet())
        {
            if (!selector.containsField( entry.getKey() ))
            {
                continue;
            }
            if (isPrimitive( entry.getValue().getClass() ))
            {
                model.addProperty( entry.getKey(), entry.getValue() );
            }
            else
            {
                HierarchicalModel<?> childModel = model.createChildMap( entry.getKey() );
                traverse( entry.getValue(), selector.getChildSelector( entry.getKey() ), childModel, context );
            }
        }
        context.emitEvent( new RenderingEvent( RenderingEventType.POJO_CHILD, model, map, map
                .getClass(), context, selector ) );
    }
    */

    protected void traversePojo( Object instance, Selector selector, MapHierarchicalModel<?> model,
            YogaRequestContext context )
    {
        Class<?> instanceType = _classFinderStrategy.findClass( instance );
        addInstanceFields( instance, instanceType, model, selector, context );

        context.emitEvent( new RenderingEvent( RenderingEventType.POJO_CHILD, model, instance,
                instanceType, context, selector ) );
    }

    public void addInstanceFields( Object instance, Class<?> instanceType,
            MapHierarchicalModel<?> model, Selector selector, YogaRequestContext requestContext )
    {
        Collection<String> fieldNames = selector.getSelectedFieldNames( instanceType );

        for (String fieldName : fieldNames)
        {
            if (!PropertyUtils.isReadable( instance, fieldName ))
            {
                // this could be either a FieldPopulator value or a mistake by
                // the user
                continue;
            }
            try
            {
                Object value = PropertyUtils.getNestedProperty( instance, fieldName );
                traverseValue( instanceType, model, selector, requestContext, fieldName, value );
            }
            catch (Exception e)
            {
                throw new YogaRuntimeException( e );
            }
        }
    }

    public void traverseValue( Class<?> parentType, MapHierarchicalModel<?> parentModel,
            Selector parentSelector, YogaRequestContext requestContext, String fieldName,
            Object fieldValue )
    {
        if (fieldValue != null)
        {
            if (isPrimitive( fieldValue.getClass() ))
            {
                parentModel.addProperty( fieldName, fieldValue );
            }
            else
            {
                Selector childSelector = parentSelector.getChildSelector( parentType, fieldName );
                if (Iterable.class.isAssignableFrom( fieldValue.getClass() ))
                {
                    traverseIterable( (Iterable<?>) fieldValue, childSelector,
                            parentModel.createChildList( fieldName ), requestContext );
                }
                else
                {
                    traversePojo( fieldValue, childSelector, parentModel.createChildMap( fieldName ),
                            requestContext );
                }
            }
        }
    }

    // GETTERS / SETTERS
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        this._classFinderStrategy = classFinderStrategy;
    }
}
