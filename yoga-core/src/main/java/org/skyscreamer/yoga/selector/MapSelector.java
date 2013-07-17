package org.skyscreamer.yoga.selector;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MapSelector implements Selector
{
    protected ConcurrentHashMap<Class<?>, Collection<Property>> descriptors = new ConcurrentHashMap<Class<?>, Collection<Property>>();

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        Collection<Property> fieldCollection = getRegisteredFieldCollection( instanceType );
        return fieldCollection != null && fieldCollection.contains( property );
    }

    protected Collection<Property> getRegisteredFieldCollection( Class<?> instanceType )
    {
        return descriptors.get( instanceType );
    }

    @Override
    public Collection<Property> getSelectedFields( Class<?> instanceType ) 
    {
        Collection<Property> fieldCollection = getRegisteredFieldCollection( instanceType );
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
        return getSelectedFields( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return false;
    }
}
