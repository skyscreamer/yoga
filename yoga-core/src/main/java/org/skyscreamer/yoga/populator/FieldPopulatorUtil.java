package org.skyscreamer.yoga.populator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.yoga.annotations.ExtraField;

public class FieldPopulatorUtil
{
    public static List<Method> getPopulatorExtraFieldMethods( Object populator, Class<?> instanceType )
    {
        List<Method> result = new ArrayList<Method>();
        if ( populator != null )
        {
            for ( Method method : populator.getClass().getDeclaredMethods() )
            {
                if ( method.isAnnotationPresent( ExtraField.class )
                        && isPopulatorField( instanceType, method.getParameterTypes() ) )
                {
                    result.add( method );
                }
            }
        }
        return result;
    }


    public static boolean isPopulatorField( Class<?> instanceType, Class<?>[] parameterTypes )
    {
        return parameterTypes.length == 0
                || (parameterTypes.length == 1 && parameterTypes[0].equals( instanceType ));
    }

}
