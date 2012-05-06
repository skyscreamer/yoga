package org.skyscreamer.yoga.metadata;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class PropertyUtil
{
    public static List<PropertyDescriptor> getReadableProperties(Class<?> instanceType)
    {
        List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();
        for (PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors( instanceType ))
        {
            if (descriptor.getReadMethod() != null && !descriptor.getName().equals( "class" ))
            {
                result.add( descriptor );
            }
        }
        return result;
    }
}
