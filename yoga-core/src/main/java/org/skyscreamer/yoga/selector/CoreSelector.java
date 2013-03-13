package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;

public class CoreSelector extends MapSelector
{
    private EntityConfigurationRegistry _entityConfigurationRegistry = new DefaultEntityConfigurationRegistry();
    protected ConcurrentHashMap<Class<?>, Collection<Property>> allCoreFields = new ConcurrentHashMap<Class<?>, Collection<Property>>();

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
    protected Collection<Property> getRegisteredFieldCollection( Class<?> instanceType )
    {
        return getProperties(instanceType, descriptors);
    }

	private Collection<Property> getProperties(Class<?> instanceType,
            ConcurrentHashMap<Class<?>, Collection<Property>> map)
    {
	    Collection<Property> properties = map.get( instanceType );
        if (properties == null)
        {
            properties = createCoreFieldsCollection( instanceType );
            Collection<Property> existingProperties = map.putIfAbsent( instanceType, properties );
            if( existingProperties != null )
            {
                properties = existingProperties;
            }
        }
        return properties;
    }

    private Collection<Property> createCoreFieldsCollection( Class<?> instanceType )
    {
        List<Property> response = new ArrayList<Property>();
        List<PropertyDescriptor> readableProperties = PropertyUtil
                .getReadableProperties( instanceType );
        Collection<String> allowedCoreFields = getConfiguredCoreFields( instanceType );
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

    protected Collection<String> getConfiguredCoreFields( Class<?> instanceType )
    {
        YogaEntityConfiguration<?> entityConfiguration = getConfig(instanceType);
        if(entityConfiguration != null)
        {
            return entityConfiguration.getCoreFields();
        }
        return null;
    }

    private YogaEntityConfiguration<?> getConfig(Class<?> instanceType)
    {
        return _entityConfigurationRegistry != null 
                ? _entityConfigurationRegistry.getEntityConfiguration(instanceType) 
                : null;
    }

    @Override
    public Collection<Property> getAllPossibleFields( Class<?> instanceType )
    {
        return createAllFieldList(instanceType);
    }

    private Collection<Property> createAllFieldList(Class<?> instanceType)
    {
        Map<String, Property> response = new TreeMap<String, Property>();

        // Get the getters
        for (PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ))
        {
            response.put( property.getName(), new PojoProperty( property ) );
        }

        // Add @ExtraField methods from the YogaEntityConfiguration, if one exists
        YogaEntityConfiguration<?> entityConfiguration = getConfig(instanceType);
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
