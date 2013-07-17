package org.skyscreamer.yoga.mapper;

import static org.skyscreamer.yoga.util.ObjectUtil.isPrimitive;

import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Property;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;

/**
 * ResultTraverser 
 * @author solomon.duskis
 *
 */

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

        // TODO: what should we do about a Map?
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

    protected void traverseIterable( Iterable<?> iterable, Selector selector,
            ListHierarchicalModel<?> model, YogaRequestContext context )
    {
        for (Object o : iterable)
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
        context.emitEvent( RenderingEventType.LIST_CHILD, model, iterable, iterable.getClass(),
                context, selector );
    }

    protected void traversePojo( Object instance, Selector selector, MapHierarchicalModel<?> model,
            YogaRequestContext context )
    {
        Class<?> instanceType = _classFinderStrategy.findClass( instance );
        addInstanceFields( instance, instanceType, model, selector, context );

        context.emitEvent( RenderingEventType.POJO_CHILD, model, instance, instanceType, context,
                selector );
    }

    protected void addInstanceFields( Object instance, Class<?> instanceType,
            MapHierarchicalModel<?> model, Selector selector, YogaRequestContext requestContext )
    {
        Iterable<Property> properties = selector.getSelectedFields( instanceType );

        for (Property property : properties)
        {
            Object value = property.getValue( instance );
            String fieldName = property.name();
            if (value == null)
            {
                continue;
            }
            if (isPrimitive( value.getClass() ))
            {
                model.addProperty( fieldName, value );
            }
            else
            {
                Selector childSelector = selector.getChildSelector( instanceType, fieldName );
                if (Iterable.class.isAssignableFrom( value.getClass() ))
                {
                    traverseIterable( (Iterable<?>) value, childSelector,
                            model.createChildList( fieldName ), requestContext );
                }
                else
                {
                    traversePojo( value, childSelector,
                            model.createChildMap( fieldName ), requestContext );
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
