package org.skyscreamer.yoga.selector;

import java.lang.reflect.Method;

public interface Property<T>
{
    String name();

    Object getValue( T instance );

    Method getReadMethod();

    boolean isPrimitive();
}
