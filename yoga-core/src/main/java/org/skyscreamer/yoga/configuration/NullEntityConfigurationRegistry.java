package org.skyscreamer.yoga.configuration;


/**
 * Null value implementation of the EntityConfigurationRegistry.
 *
 * @see EntityConfigurationRegistry
 * @see YogaEntityConfiguration
 */
public class NullEntityConfigurationRegistry implements EntityConfigurationRegistry
{

    public void register( YogaEntityConfiguration<?>... entityConfigurations )
    {
        throw new IllegalArgumentException( "Please create a new DefaultEntityConfigurationRegistry before using register" );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> YogaEntityConfiguration<T> getEntityConfiguration( Class<T> clazz )
    {
        return null;
    }
}
