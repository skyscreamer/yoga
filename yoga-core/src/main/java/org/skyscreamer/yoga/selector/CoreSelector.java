package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;

public class CoreSelector extends MapSelector
{
    private EntityConfigurationRegistry _entityConfigurationRegistry = new DefaultEntityConfigurationRegistry();

    public CoreSelector( EntityConfigurationRegistry entityConfigurationRegistry )
    {
        _entityConfigurationRegistry = entityConfigurationRegistry;
    }

    public CoreSelector()
    {
    }

    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry )
    {
        _entityConfigurationRegistry = entityConfigurationRegistry;
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
        YogaEntityConfiguration entityConfiguration = _entityConfigurationRegistry == null ? null
                : _entityConfigurationRegistry.getEntityConfiguration(instanceType);
        return entityConfiguration != null ? entityConfiguration.getCoreFields() : null;
    }

    @Override
    public Collection<Property> getAllPossibleFields( Class<?> instanceType )
    {
        Map<String, Property> response = new TreeMap<String, Property>();

        // Get the getters
        List<PropertyDescriptor> readableProperties = PropertyUtil
                .getReadableProperties( instanceType );
        for (PropertyDescriptor property : readableProperties)
        {
            response.put( property.getName(), new PojoProperty( property ) );
        }

        // Add @ExtraField methods from the YogaEntityConfiguration, if one exists
        YogaEntityConfiguration<?> entityConfiguration = _entityConfigurationRegistry == null ? null
                : _entityConfigurationRegistry.getEntityConfiguration(instanceType);
        if (entityConfiguration != null)
        {
            for (Method method : entityConfiguration.getExtraFieldMethods())
            {
                String name = method.getAnnotation( ExtraField.class ).value();
                response.put(name, new ExtraFieldProperty( name, entityConfiguration, method ) );
            }
        }

        return response.values();
    }
}
