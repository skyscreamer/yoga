package org.skyscreamer.yoga.demo.util;

import java.lang.reflect.ParameterizedType;

public class TypeUtils
{
    // http://blog.xebia.com/2009/02/acessing-generic-types-at-runtime-in-java/
    @SuppressWarnings( "unchecked" )
    public static <T> Class<T> returnedClass(Class<?> type)
    {
        ParameterizedType parameterizedType = (ParameterizedType) type.getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

}
