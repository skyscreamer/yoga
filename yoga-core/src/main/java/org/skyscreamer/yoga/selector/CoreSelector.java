package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.NullEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.metadata.PropertyUtil;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class CoreSelector implements Selector
{
    private EntityConfigurationRegistry _entityConfigurationRegistry;
    protected final ConcurrentHashMap<Class, Map> _coreFields = new ConcurrentHashMap<Class, Map>();
    protected final ConcurrentHashMap<Class, Map> _allFields = new ConcurrentHashMap<Class, Map>();

    public CoreSelector( EntityConfigurationRegistry entityConfigurationRegistry )
    {
        _entityConfigurationRegistry = entityConfigurationRegistry;
    }

    public CoreSelector()
    {
    	_entityConfigurationRegistry = new NullEntityConfigurationRegistry();
    }

    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry )
    {
        _entityConfigurationRegistry = entityConfigurationRegistry;
    }

    public EntityConfigurationRegistry getEntityConfigurationRegistry()
    {
        return _entityConfigurationRegistry;
    }

    @Override
    public <T> Property<T> getProperty( Class<T> instanceType, String fieldName )
    {
        Map<String, Property<T>> properties = getAllPossibleFieldMap( instanceType );
        return properties != null ? properties.get( fieldName ) : null;
    }

    private <T> Map<String, Property<T>> getProperties( Class<T> instanceType, ConcurrentHashMap map )
    {
        Map properties = ( Map ) map.get( instanceType );
        if (properties == null)
        {
            properties = createCoreFieldsCollection( instanceType );
            Map existingProperties = ( Map ) map.putIfAbsent( instanceType, properties );
            if (existingProperties != null)
            {
                properties = existingProperties;
            }
        }
        return properties;
    }

    private <T> Map<String, Property<T>> createCoreFieldsCollection( Class<T> instanceType )
    {
        Map<String, Property<T>> response = new HashMap<String, Property<T>>();
        List<PropertyDescriptor> readableProperties = PropertyUtil.getReadableProperties( instanceType );

        YogaEntityConfiguration<T> config = getConfig( instanceType );
        Collection<String> allowedCoreFields = config != null ? config.getCoreFields() : null;
        Collection<Property<T>> properties = config == null ? null : config.getProperties();

        if (allowedCoreFields == null)
        {
            for ( PropertyDescriptor descriptor : readableProperties )
            {
                if (descriptor.getReadMethod().isAnnotationPresent( Core.class ))
                {
                    response.put( descriptor.getName(), createProperty( properties, instanceType, descriptor ) );
                }
            }
        }
        else
        {
            for ( PropertyDescriptor descriptor : readableProperties )
            {
                if (allowedCoreFields.contains( descriptor.getName() ))
                {
                    response.put( descriptor.getName(), createProperty( properties, instanceType, descriptor ) );
                }
            }
        }

        return response;
    }

    protected <T> Property<T> createProperty( Collection<Property<T>> properties, Class<T> instanceType,
            PropertyDescriptor desc )
    {
        if (properties != null)
        {
            for ( Property<T> property : properties )
            {
                if (property.name().equals( desc.getName() ))
                {
                    return property;
                }
            }
        }
        return new PojoProperty<T>( desc );
    }

    private <T> YogaEntityConfiguration<T> getConfig( Class<T> instanceType )
    {
        return _entityConfigurationRegistry != null ? _entityConfigurationRegistry
                .getEntityConfiguration( instanceType ) : null;
    }

    @Override
    public <T> Map<String, Property<T>> getAllPossibleFieldMap( Class<T> instanceType )
    {
        Map response = _allFields.get( instanceType );
        if (response == null)
        {
            Map existing = _allFields.putIfAbsent( instanceType, response = createAllFieldMap( instanceType ) );
            if (existing != null)
                response = existing;
        }
        return response;
    }

    protected <T> Map<String, Property<T>> createAllFieldMap( Class<T> instanceType )
    {
        Map<String, Property<T>> response = new TreeMap<String, Property<T>>();

        YogaEntityConfiguration<T> config = getConfig( instanceType );

        // Get the getters
        List<PropertyDescriptor> getters = PropertyUtil.getReadableProperties( instanceType );
        Map<String, Property<T>> getterMap = new TreeMap<String, Property<T>>();

        Collection<Property<T>> properties = config == null ? null : config.getProperties();
        for ( PropertyDescriptor descriptor : getters )
        {
            String name = descriptor.getName();
            getterMap.put( name, createProperty( properties, instanceType, descriptor ) );
        }
        
        // Add @ExtraField methods from the YogaEntityConfiguration, if one
        // exists
        if (config != null)
        {
            response.putAll( getProperties( instanceType, _coreFields ) );

            Collection<String> selectableFields = config == null ? null : config.getSelectableFields();
            if( selectableFields != null )
            {
                for( String field : selectableFields )
                {
                    response.put( field, getterMap.get( field ) );
                }
            }
            else
            {
                response = getterMap;
            }

            for ( Method method : config.getExtraFieldMethods() )
            {
                String name = method.getAnnotation( ExtraField.class ).value();
                response.put( name, new ExtraFieldProperty<T>( name, config, method ) );
            }
        }
        else
        {
            return getterMap;
        }
        return response;
    }

    @Override
    public <T> Collection<Property<T>> getSelectedFields( Class<T> instanceType )
    {
        return getProperties( instanceType, _coreFields ).values();
    }

    @Override
    public boolean containsField( Class<?> instanceType, String fieldName )
    {
        return getAllPossibleFieldMap( instanceType ).containsKey( fieldName );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return false;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        return this;
    }
}
