package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.metadata.PropertyUtil;

public class CoreSelector extends MapSelector
{
    @Override
    protected Set<String> getFieldCollection( Class<?> instanceType )
    {
        Set<String> fields = descriptors.get( instanceType );
        if (fields == null)
        {
            descriptors.put( instanceType, fields = createFields( instanceType ) );
        }
        return fields;
    }

    private Set<String> createFields( Class<?> instanceType )
    {
        Set<String> response = new TreeSet<String>();
        List<PropertyDescriptor> readableProperties = PropertyUtil.getReadableProperties( instanceType );

        for (PropertyDescriptor property : readableProperties)
        {
            Method readMethod = property.getReadMethod();
            if (readMethod.isAnnotationPresent( Core.class ))
            {
                response.add( property.getName() );
            }
        }
        return response;
    }
    
    @Override
    public Set<String> getAllPossibleFields( Class<?> instanceType )
    {
        Set<String> response = new TreeSet<String>();
        List<PropertyDescriptor> readableProperties = PropertyUtil.getReadableProperties( instanceType );

        for (PropertyDescriptor property : readableProperties)
        {
            response.add( property.getName() );
        }
        return response;
    }

}
