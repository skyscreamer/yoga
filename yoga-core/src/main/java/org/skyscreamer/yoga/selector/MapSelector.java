package org.skyscreamer.yoga.selector;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class MapSelector implements Selector
{
    protected ConcurrentHashMap<Class, Map> descriptors = new ConcurrentHashMap<Class, Map>();

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        Collection fieldCollection = getRegisteredFieldCollection( instanceType );
        return fieldCollection != null && fieldCollection.contains( property );
    }

    protected <T> Collection<Property<T>> getRegisteredFieldCollection( Class<T> instanceType )
    {
        return descriptors.get( instanceType ).values();
    }

    @Override
    public <T> Collection<Property<T>> getSelectedFields( Class<T> instanceType ) 
    {
        Collection fieldCollection = getRegisteredFieldCollection( instanceType );
        return fieldCollection == null ? Collections.emptySet() : Collections.unmodifiableCollection( fieldCollection );
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        return this;
    }

    @Override
    public <T> Collection<Property<T>> getAllPossibleFields( Class<T> instanceType )
    {
        return getSelectedFields( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return false;
    }
}
