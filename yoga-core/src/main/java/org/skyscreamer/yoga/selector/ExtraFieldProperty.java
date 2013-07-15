package org.skyscreamer.yoga.selector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.util.ObjectUtil;

public class ExtraFieldProperty<T> implements Property<T>
{

    private String name;
    private YogaEntityConfiguration<T> entityConfiguration;
    private Method method;
	private boolean isPrimitive;

    public ExtraFieldProperty( String name, YogaEntityConfiguration<T> entityConfiguration, Method method )
    {
        this.name = name;
        this.entityConfiguration = entityConfiguration;
        this.method = method;
        this.isPrimitive = ObjectUtil.isPrimitive(method.getReturnType());
    }

    @Override
    public String name()
    {
        return name;
    }

    @Override
    public Method getReadMethod()
    {
        return method;
    }

    @Override
    public Object getValue( T instance )
    {
        try {
            return getEntityConfigurationValue( method, entityConfiguration, instance );
        } catch (Exception e) {
            throw new YogaRuntimeException( "Could not invoke " + method + " on " + entityConfiguration.getClass().getName(), e );
        }
    }

    protected Object getEntityConfigurationValue( Method method, YogaEntityConfiguration<?> entityConfiguration, T instance )
            throws InvocationTargetException, IllegalAccessException
    {
        switch (method.getParameterTypes().length)
        {
            case 0:
                return method.invoke(entityConfiguration);

            case 1:
                return method.invoke(entityConfiguration, instance);

            default:
                throw new YogaRuntimeException(
                        String.format(
                                "An @ExtraField method can only have 0 or 1 parameters.  Method %s has %d",
                                method.toString(), method.getParameterTypes().length ) );
        }
    }

	@Override
    public boolean isPrimitive()
    {
	    return isPrimitive;
    }
}
