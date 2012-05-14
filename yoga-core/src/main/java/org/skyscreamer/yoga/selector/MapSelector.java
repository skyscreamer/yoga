package org.skyscreamer.yoga.selector;

import java.util.*;

public abstract class MapSelector implements Selector
{
    protected Map<Class<?>, Set<String>> descriptors = new HashMap<Class<?>, Set<String>>();

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        Set<String> fieldCollection = getFieldCollection( instanceType );
        return fieldCollection == null ? false : fieldCollection.contains( property );
    }

    protected Set<String> getFieldCollection( Class<?> instanceType )
    {
        return descriptors.get( instanceType );
    }

    @Override
    public Set<String> getSelectedFieldNames( Class<?> instanceType )
    {
        Set<String> fieldCollection = getFieldCollection( instanceType );
        return fieldCollection == null ? Collections.<String> emptySet() : Collections.unmodifiableSet( fieldCollection );
    }

    @Override
    public Map<String, Selector> getSelectors( Class<?> instanceType )
    {
        Map<String, Selector> result = new TreeMap<String, Selector>();
        Set<String> fields = getFieldCollection( instanceType );
        if (fields != null)
        {
            for (String field : fields)
            {
                result.put( field, getChildSelector( instanceType, field ) );
            }
        }
        return result;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        return this;
    }

    @Override
    public Set<String> getAllPossibleFields( Class<?> instanceType )
    {
        return getSelectedFieldNames( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return false;
    }
}
