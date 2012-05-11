package org.skyscreamer.yoga.selector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FieldSelector implements Selector
{
    protected Map<String, Selector> subSelectors = new HashMap<String, Selector>();

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

    
    @Override
    public Set<String> getSelectedFieldNames( Class<?> instanceType )
    {
        return getFieldNames();
    }

    public Set<String> getFieldNames()
    {
        return Collections.unmodifiableSet( subSelectors.keySet() );
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
