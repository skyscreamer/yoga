package org.skyscreamer.yoga.util;

import java.util.Date;

public class ObjectUtil
{
    public static boolean isPrimitive( Class<?> clazz )
    {
        return clazz.isPrimitive() || clazz.isEnum() || Number.class.isAssignableFrom( clazz )
                || String.class.isAssignableFrom( clazz ) || Boolean.class.isAssignableFrom( clazz )
                || Character.class.isAssignableFrom( clazz ) || Date.class.isAssignableFrom( clazz );
    }
}
