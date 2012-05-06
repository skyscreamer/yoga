package org.skyscreamer.yoga.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class ObjectUtil
{

    public static Object getFieldValue( Object instance, String propertyName )
    {
        try
        {
            return PropertyUtils.getNestedProperty( instance, propertyName );
        }
        catch ( Exception e )
        {
            throw new YogaRuntimeException( e );
        }
    }

    public static boolean isPrimitive( Class<?> clazz )
    {
        return clazz.isPrimitive() || clazz.isEnum() || Number.class.isAssignableFrom( clazz )
                || String.class.isAssignableFrom( clazz ) || Boolean.class.isAssignableFrom( clazz )
                || Character.class.isAssignableFrom( clazz );
    }
}
