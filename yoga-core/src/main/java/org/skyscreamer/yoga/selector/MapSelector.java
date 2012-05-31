package org.skyscreamer.yoga.selector;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class MapSelector implements Selector
{
    protected Map<Class<?>, Collection<Property>> descriptors = new HashMap<Class<?>, Collection<Property>>();

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        Collection<Property> fieldCollection = getFieldCollection( instanceType );
        return fieldCollection == null ? false : fieldCollection.contains( property );
    }

    protected Collection<Property> getFieldCollection( Class<?> instanceType )
    {
        return descriptors.get( instanceType );
    }

    @Override
    public Collection<Property> getSelectedFields(Class<?> instanceType, Object instance) 
    {
        Collection<Property> fieldCollection = getFieldCollection( instanceType );
        return fieldCollection == null ? Collections.<Property> emptySet() : Collections.unmodifiableCollection( fieldCollection );
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        return this;
    }

    @Override
    public Collection<Property> getAllPossibleFields( Class<?> instanceType )
    {
        return getSelectedFields( instanceType, null );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return false;
    }
}
