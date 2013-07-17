package org.skyscreamer.yoga.selector;

import java.lang.reflect.Method;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class NamedProperty implements Property
{

    private String name;

    public NamedProperty( String name )
    {
        this.name = name;
    }

    @Override
    public String name()
    {
        return name;
    }

    @Override
    public Object getValue( Object instance )
    {
        throw new YogaRuntimeException( "Named Properties don't read properties" );
    }

    @Override
    public Method getReadMethod()
    {
        throw new YogaRuntimeException( "Named Properties don't have a read method" );
    }

}
