package org.skyscreamer.yoga.selector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class ExtraFieldProperty implements Property
{

    private String name;
    private YogaEntityConfiguration entityConfiguration;
    private Method method;

    public ExtraFieldProperty( String name, YogaEntityConfiguration entityConfiguration, Method method )
    {
        this.name = name;
        this.entityConfiguration = entityConfiguration;
        this.method = method;
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
    public Object getValue( Object instance )
    {
        try {
            return getEntityConfigurationValue( method, entityConfiguration, instance );
        } catch (Exception e) {
            throw new YogaRuntimeException( "Could not invoke " + method + " on " + entityConfiguration.getClass().getName(), e );
        }
    }

    protected Object getEntityConfigurationValue( Method method, YogaEntityConfiguration entityConfiguration, Object instance )
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
}
