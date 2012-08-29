package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.CoreFields;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;

public class CoreSelector extends MapSelector
{
    private FieldPopulatorRegistry _populatorRegistry;

    public CoreSelector( FieldPopulatorRegistry populatorRegistry )
    {
        _populatorRegistry = populatorRegistry;
    }

    public CoreSelector()
    {
    }

    public void setPopulatorRegistry( FieldPopulatorRegistry populatorRegistry )
    {
        this._populatorRegistry = populatorRegistry;
    }
    
    @Override
    protected Collection<Property> getFieldCollection( Class<?> instanceType )
    {
        Collection<Property> properties = descriptors.get( instanceType );
        if (properties == null)
        {
            descriptors.put( instanceType, properties = createFields( instanceType ) );
        }
        return properties;
    }

    private Collection<Property> createFields( Class<?> instanceType )
    {
        List<Property> response = new ArrayList<Property>();
        List<PropertyDescriptor> readableProperties = PropertyUtil
        		.getReadableProperties( instanceType );
        Collection<String> allowedCoreFields = getAllowedCoreFields( instanceType );
        if (allowedCoreFields == null)
        {
            for (PropertyDescriptor property : readableProperties)
            {
                if (property.getReadMethod().isAnnotationPresent( Core.class ))
                {
                    response.add( new PojoProperty( property ) );
                }
            }
        }
        else
        {
            for (PropertyDescriptor property : readableProperties)
            {
                if (allowedCoreFields.contains( property.getName() ))
                {
                    response.add( new PojoProperty( property ) );
                }
            }
        }

        return response;
    }

    @SuppressWarnings("unchecked")
    protected Collection<String> getAllowedCoreFields( Class<?> instanceType )
    {
        Object populator = _populatorRegistry == null ? null : _populatorRegistry
                .getFieldPopulator( instanceType );
        if (populator != null)
        {
            for (Method method : populator.getClass().getMethods())
            {
                try
                {
                    if (method.isAnnotationPresent( CoreFields.class ))
                    {
                        return (Collection<String>) method.invoke( populator );
                    }
                }
                catch (Exception e)
                {
                    // @CoreFields not found on method of return type
                    // List<String>
                }
            }
        }
        return null;
    }

    @Override
    public Collection<Property> getAllPossibleFields( Class<?> instanceType )
    {
        Map<String, Property> response = new TreeMap<String, Property>();
        List<PropertyDescriptor> readableProperties = PropertyUtil
                .getReadableProperties( instanceType );

        for (PropertyDescriptor property : readableProperties)
        {
            response.put( property.getName(), new PojoProperty( property ) );
        }

        Object populator = _populatorRegistry == null ? null : _populatorRegistry
                .getFieldPopulator( instanceType );
        if (populator != null)
        {
            for (Method method : populator.getClass().getMethods())
            {
                Class<?>[] parameterTypes = method.getParameterTypes();
                int paramLength = parameterTypes.length;
                if (method.isAnnotationPresent( ExtraField.class ) && paramLength < 2)
                {
                    String name = method.getAnnotation( ExtraField.class ).value();
                    response.put(name, new ExtraFieldProperty( name, populator, method ) );
                }
            }
        }
        return response.values();
    }
}
