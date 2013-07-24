package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.util.ObjectUtil;

public class PojoProperty<T> implements Property<T>
{

    private PropertyDescriptor property;
    private Method readMethod;
	private boolean isPrimitive;


    public PojoProperty( PropertyDescriptor property )
    {
        this.property = property;
        readMethod = property.getReadMethod();
        isPrimitive = ObjectUtil.isPrimitive(readMethod.getReturnType());
    }

    @Override
    public String name()
    {
        return property.getName();
    }

    @Override
    public Object getValue( T instance )
    {
        try
        {
            return readMethod.invoke( instance );
//          return PropertyUtils.getProperty( instance, property.getName() );
        }
        catch (Exception e)
        {
            throw new YogaRuntimeException("Could not read " + property.getName() + " from " + instance, e);
        }
    }

    @Override
    public Method getReadMethod()
    {
        return readMethod;
    }

	@Override
    public boolean isPrimitive()
    {
	    return isPrimitive;
    }

}
