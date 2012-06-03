package org.skyscreamer.yoga.selector;

import java.lang.reflect.Method;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class ExtraFieldProperty implements Property
{

    private String name;
    private Object populator;
    private Method method;

    public ExtraFieldProperty( String name, Object populator, Method method )
    {
        this.name = name;
        this.populator = populator;
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
        return getPopulatorFieldValue( method, populator, instance );
    }

    protected Object getPopulatorFieldValue( Method method, Object populator, Object instance )
    {
        switch (method.getParameterTypes().length)
        {
            case 0:
                return retrievePopulatorFieldValue( method, populator );

            case 1:
                return retrievePopulatorFieldValue( method, populator, instance );

            default:
                throw new YogaRuntimeException(
                        String.format(
                                "A Field Populator method can only have 0 or 1 parameters.  Method %s has %d",
                                method.toString(), method.getParameterTypes().length ) );
        }
    }

    protected Object retrievePopulatorFieldValue( Method method, Object populator, Object... args )
    {
        try
        {
            return method.invoke( populator, args );
        }
        catch (Exception e)
        {
            throw new YogaRuntimeException( "Could not invoke " + method, e );
        }
    }

}
