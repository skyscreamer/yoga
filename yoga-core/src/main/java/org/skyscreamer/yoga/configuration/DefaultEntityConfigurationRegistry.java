package org.skyscreamer.yoga.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of the EntityConfigurationRegistry.
 *
 * @see EntityConfigurationRegistry
 * @see YogaEntityConfiguration
 */
public class DefaultEntityConfigurationRegistry implements EntityConfigurationRegistry
{
    protected Map<Class<?>, YogaEntityConfiguration<?>> _registry = new HashMap<Class<?>, YogaEntityConfiguration<?>>();

    /**
     * A default constructor.
     */
    public DefaultEntityConfigurationRegistry()
    {
    }

    /**
     * Constructs a registry and initializes it with an array of initial configurations.
     * @param entityConfigurations Initial configurations to register
     */
    public DefaultEntityConfigurationRegistry( YogaEntityConfiguration<?>... entityConfigurations )
    {
        register( entityConfigurations );
    }

    /**
     * Constructs a registry and initializes it with an list of initial configurations.
     * @param entityConfigurations Initial configurations to register
     */
    public DefaultEntityConfigurationRegistry( List<YogaEntityConfiguration<?>> entityConfigurations )
    {
        register( entityConfigurations.toArray(new YogaEntityConfiguration[0]) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register( YogaEntityConfiguration<?>... entityConfigurations )
    {
        for ( YogaEntityConfiguration<?> entityConfiguration : entityConfigurations )
        {
            Class<?> type = entityConfiguration.getEntityClass();
            if ( type == null )
            {
                throw new IllegalArgumentException("Entity configuration must define an entity class to configure: "
                        + entityConfiguration.getClass().getName());
            }
            _registry.put( type, entityConfiguration );
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> YogaEntityConfiguration<T> getEntityConfiguration( Class<T> clazz )
    {
        return (YogaEntityConfiguration<T>) _registry.get( clazz );
    }
}
