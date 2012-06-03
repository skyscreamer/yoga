package org.skyscreamer.yoga.selector;

import java.lang.reflect.Method;

public interface Property
{
    String name();

    Object getValue( Object instance );
    
    Method getReadMethod();
}
