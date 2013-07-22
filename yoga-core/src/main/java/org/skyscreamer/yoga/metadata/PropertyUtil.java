package org.skyscreamer.yoga.metadata;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class PropertyUtil
{
    public static List<PropertyDescriptor> getReadableProperties( Class<?> instanceType )
    {
        List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();
        for ( PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors( instanceType ) )
        {
            if ( descriptor.getReadMethod() != null && !descriptor.getName().equals( "class" ) )
            {
                result.add( descriptor );
            }
        }
        return result;
    }
}
