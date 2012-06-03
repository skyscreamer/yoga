package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class PojoProperty implements Property
{

    private PropertyDescriptor property;


    public PojoProperty( PropertyDescriptor property )
    {
        this.property = property;
    }

    @Override
    public String name()
    {
        return property.getName();
    }

    @Override
    public Object getValue( Object instance )
    {
        try
        {
            return PropertyUtils.getProperty( instance, property.getName() );
        }
        catch (Exception e)
        {
            throw new YogaRuntimeException("Could not read " + property.getName() + " from " + instance, e);
        }
    }

    @Override
    public Method getReadMethod()
    {
        return property.getReadMethod();
    }

}
