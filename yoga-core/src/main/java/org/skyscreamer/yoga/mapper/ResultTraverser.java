package org.skyscreamer.yoga.mapper;

import static org.skyscreamer.yoga.util.ObjectUtil.isPrimitive;

import java.io.IOException;

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
            YogaRequestContext context ) throws IOException
    {
        if (instance != null)
        {
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

    }

    protected void traverseIterable( Iterable<?> iterable, Selector selector,
            ListHierarchicalModel<?> model, YogaRequestContext context ) throws IOException
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
        context.emitEvent( model, iterable, context, selector );
        model.finished();
    }

    protected <T> void traversePojo( T instance, Selector selector, MapHierarchicalModel<?> model,
            YogaRequestContext context ) throws IOException
    {
        Class<T> instanceType = _classFinderStrategy.findClass( instance );
        addInstanceFields( instance, instanceType, model, selector, context );

        context.emitEvent( model, instance, instanceType, context, selector );
        model.finished();
    }

    protected <T> void addInstanceFields( T instance, Class<T> instanceType,
            MapHierarchicalModel<?> model, Selector selector, YogaRequestContext requestContext ) throws IOException
    {
        Iterable<Property<T>> properties = selector.getSelectedFields( instanceType );

        for (Property<T> property : properties)
        {
            Object value = property.getValue( instance );
            if (value == null)
            {
                continue;
            }

            String fieldName = property.name();
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
