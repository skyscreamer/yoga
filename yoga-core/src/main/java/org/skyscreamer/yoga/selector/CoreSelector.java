package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.populator.FieldPopulator;

public class CoreSelector implements Selector
{
    @Override
    public Selector getField(String fieldName)
    {
        return this;
    }

    @Override
    public boolean containsField(PropertyDescriptor property, FieldPopulator fieldPopulator)
    {
        Method readMethod = property.getReadMethod();
        boolean isCore = readMethod.isAnnotationPresent( Core.class );
        if (!isCore && fieldPopulator != null)
        {
            isCore = fieldPopulator.getCoreFields().contains( property.getName() );
        }
        return isCore;
    }

    @Override
    public boolean containsField(String property)
    {
        return false;
    }

    @Override
    public Set<String> getFieldNames()
    {
        return Collections.emptySet();
    }
    
    @Override
    public Map<String, Selector> getFields() {
        return Collections.emptyMap();
    }
}
