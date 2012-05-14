package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;

import java.lang.reflect.Method;
import java.util.*;

public class FieldSelector implements Selector
{
    protected Map<String, Selector> subSelectors = new HashMap<String, Selector>();
    protected FieldPopulatorRegistry _fieldPopulatorRegistry;

    public FieldSelector( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        _fieldPopulatorRegistry = fieldPopulatorRegistry;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        return getSelector( fieldName );
    }

    public Selector getSelector( String fieldName )
    {
        return subSelectors.get( fieldName );
    }

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        return containsField( property );
    }

    public boolean containsField( String property )
    {
        return subSelectors.containsKey( property );
    }

    
    @SuppressWarnings( "unchecked" )
    public Set<String> getSelectedFieldNames( Class<?> instanceType )
    {
        Set<String> fieldNames = getFieldNames();
        Object fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( instanceType );
        if ( fieldPopulator != null )
        {
            try
            {
                Method method = fieldPopulator.getClass().getMethod( "getSupportedFields" );
                List<String> supportedFields = (List<String>) method.invoke( fieldPopulator );
                Iterator<String> iter = fieldNames.iterator();
                while ( iter.hasNext() )
                {
                    String fieldName = iter.next();
                    if ( !supportedFields.contains( fieldName ) )
                    {
                        iter.remove();
                    }
                }
            }
            catch ( Exception e )
            {
                // getSupportedFields not implemented on FieldPopulator
            }
        }
        return fieldNames;
    }

    public Set<String> getFieldNames()
    {
        return subSelectors.keySet();
    }

    @Override
    public Map<String, Selector> getSelectors( Class<?> instanceType )
    {
        return getFields();
    }

    public Map<String, Selector> getFields()
    {
        return subSelectors;
    }

    public void register( String fieldName, FieldSelector subSelector )
    {
        subSelectors.put( fieldName, subSelector );
    }

    @Override
    public Set<String> getAllPossibleFields( Class<?> instanceType )
    {
        return getFieldNames();
    }
    
    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }

}
