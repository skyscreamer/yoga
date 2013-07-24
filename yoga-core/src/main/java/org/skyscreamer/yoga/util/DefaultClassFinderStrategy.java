package org.skyscreamer.yoga.util;

public class DefaultClassFinderStrategy implements ClassFinderStrategy
{
    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> findClass( T instance )
    {
        return (Class<T>) (instance == null ? null : instance.getClass());
    }
}
