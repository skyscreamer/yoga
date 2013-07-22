package org.skyscreamer.yoga.util;

/**
 * 
 * 
 * Created by IntelliJ IDEA. User: corby
 */
public interface ClassFinderStrategy
{
    <T> Class<T> findClass( T instance );
}
