package org.skyscreamer.yoga.util;

public class DefaultClassFinderStrategy implements ClassFinderStrategy
{
    @Override
    public Class<?> findClass( Object instance )
    {
        return instance == null ? null : instance.getClass();
    }
}
