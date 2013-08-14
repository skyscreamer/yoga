package org.skyscreamer.yoga.selector;

import java.lang.reflect.Method;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class NamedProperty<T> implements Property<T>
{

    private String name;
    private boolean isPrimitive = false;

    public NamedProperty( String name )
    {
        this.name = name;
    }

    public NamedProperty(String name, boolean isPrimitive)
    {
	    this.name = name;
	    this.isPrimitive = isPrimitive;
    }

	@Override
    public String name()
    {
        return name;
    }

    @Override
    public Object getValue( T instance )
    {
        throw new YogaRuntimeException( "Named Properties don't read properties" );
    }

    @Override
    public Method getReadMethod()
    {
        throw new YogaRuntimeException( "Named Properties don't have a read method" );
    }

	@Override
    public boolean isPrimitive()
    {
	    return isPrimitive;
    }
	
	@Override
	public String toString()
	{
	    return "NamedProperty(" + name + ")";
	}

}
