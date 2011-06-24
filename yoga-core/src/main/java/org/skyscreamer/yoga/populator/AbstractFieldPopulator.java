package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.selector.Selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public abstract class AbstractFieldPopulator<M> implements FieldPopulator<M>
{
    public Map<String,Object> populateObjectFields( M model, Selector selector )
    {
        Map<String,Object> result = new HashMap<String, Object>();

        for ( String fieldName : getCoreFieldNames() )
        {
            addFieldToResult( fieldName, selector, model, result );
        }

        for ( String fieldName : selector.getFieldNames() )
        {
            addFieldToResult( fieldName, selector, model, result );
        }

        return result;
    }

    private void addFieldToResult( String fieldName, Selector selector, M model, Map<String, Object> result )
    {
        if ( getModelFieldNames().contains( fieldName ) )
        {
            try
            {
                // TODO: Support read-only fields
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor( fieldName, model.getClass() );
                Method readMethod = propertyDescriptor.getReadMethod();
                result.put( fieldName, readMethod.invoke( model ) );
            }
            catch ( Exception e )
            {
                System.out.println( "Could not invoke getter for property " + fieldName + " on class " +
                    model.getClass().getName() );
            }
        }
        else
        {
            Object value = constructFieldValue( fieldName, model, selector );
            if ( value != null )
            {
                result.put( fieldName, value );
            }
        }
    }

    protected abstract Object constructFieldValue( String fieldName, M model, Selector selector );

    protected abstract Collection<String> getCoreFieldNames();

    protected abstract Collection<String> getModelFieldNames();

    public List<Map<String,Object>> populateListFields( Collection<M> models, Selector selector )
    {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        for ( M model : models )
        {
            result.add( populateObjectFields( model, selector ) );
        }
        return result;
    }
}
