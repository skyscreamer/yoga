package org.skyscreamer.yoga.mapper;

import static org.skyscreamer.yoga.util.ObjectUtil.isPrimitive;

import java.io.IOException;
import java.util.Map;

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
            if (instance instanceof Iterable)
            {
                traverseIterable( (Iterable<?>) instance, selector, (ListHierarchicalModel<?>) model, context );
            }
            else if (instance instanceof Map)
            {
                traverseMap( (Map<?,?>) instance, selector, (MapHierarchicalModel<?>) model, context );
            }
            else
            {
                traversePojo( instance, selector, (MapHierarchicalModel<?>) model, context );
            }
        } 
        else 
        {
        	model.finished();
        }
    }
    
    private <T> void addField(Selector selector, MapHierarchicalModel<?> model,
            YogaRequestContext context, Class<T> instanceType,
            boolean isPrimitive, Object value, String fieldName)
            throws IOException
    {
        if ( isPrimitive )
        {
            model.addProperty( fieldName, value );
            return;
        }
        
        Selector childSelector = selector.getChildSelector( instanceType, fieldName );
        if ( value instanceof Iterable )
        {
            traverseIterable( ( Iterable<?> ) value, childSelector,
                    model.createChildList( fieldName ), context );
        }
        else if ( value instanceof Map )
        {
            traverseMap( ( Map<?, ?> ) value, childSelector,
                    model.createChildMap( fieldName ), context );
        }
        else
        {
            traversePojo( value, childSelector,
                    model.createChildMap( fieldName ), context );
        }
    }


    public void traverseIterable( Iterable<?> iterable, Selector selector,
            ListHierarchicalModel<?> model, YogaRequestContext context ) throws IOException
    {
        if ( iterable != null )
        {
            for ( Object child : iterable )
            {
                if (child == null || isPrimitive( child.getClass() ))
                {
                    model.addValue( child );
                }
                else if ( child instanceof Map )
                {
                    traverseMap( ( Map<?, ?> ) child, selector, model.createChildMap(), context );
                }
                else
                {
                    traversePojo( child, selector, model.createChildMap(), context );
                }
            }
            context.emitEvent( model, iterable, context, selector );
        }
        model.finished();
    }

    private void traverseMap(Map<?, ?> map, Selector selector, MapHierarchicalModel<?> model,
        YogaRequestContext context) throws IOException
    {
        if ( map != null )
        {
            for ( Map.Entry<?, ?> entry : map.entrySet() )
            {
                String fieldName = entry.getKey().toString();
                if ( selector.containsField( Map.class, fieldName ) )
                {
                    Object value = entry.getValue();
                    if ( value != null )
                    {
                        addField( selector, model, context, Map.class,
                                isPrimitive( value.getClass() ), value,
                                fieldName );
                    }
                }
            }
            context.emitEvent( model, map, context, selector );
        }
        model.finished();
    }

    public <T> void traversePojo( T instance, Selector selector, MapHierarchicalModel<?> model,
            YogaRequestContext context ) throws IOException
    {
        if ( instance != null )
        {
            Class<T> instanceType = _classFinderStrategy.findClass( instance );
            for (Property<T> property : selector.getSelectedFields( instanceType ))
            {
                Object value = property.getValue( instance );
                if ( value != null )
                {
                    addField( selector, model, context, instanceType,
                            property.isPrimitive(), value, property.name() );
                }
            }
            context.emitEvent( model, instance, instanceType, context, selector );
        }
        model.finished();
    }

    // GETTERS / SETTERS
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        this._classFinderStrategy = classFinderStrategy;
    }

    public ClassFinderStrategy getClassFinderStrategy()
    {
        return _classFinderStrategy;
    }
}

