package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class CoreSelector implements Selector
{
    @Override
    public Selector getField( PropertyDescriptor property )
    {
        return this;
    }

    public Selector getField( String fieldName )
    {
        return this;
    }

    @Override
    public boolean containsField( PropertyDescriptor property )
    {
        return property.getReadMethod().isAnnotationPresent( Core.class );
    }

    public Set<String> getFieldNames()
    {
        return new HashSet<String>();
    }
}
