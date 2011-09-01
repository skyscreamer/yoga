package org.skyscreamer.yoga.mapper;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public interface ClassFinderStrategy
{
    Class<?> findClass( Object instance );
}
