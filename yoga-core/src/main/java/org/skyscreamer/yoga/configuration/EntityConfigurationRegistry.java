package org.skyscreamer.yoga.configuration;

/**
 * This interface defines a registry of YogaEntityConfiguration objects by class.  It allows a configuration to be
 * registered, and it allows the lookup of a configuration by class.
 *
 * @see YogaEntityConfiguration
 * @see DefaultEntityConfigurationRegistry
 */
public interface EntityConfigurationRegistry
{
    /**
     * Register one or more entity configurations.  Multiple calls to this method are additive, except in the case
     * of multiple configurations for the same entity class.  In that case the last configuration wins.
     *
     * @param entityConfigurations The entity configurations to register
     */
    void register( YogaEntityConfiguration<?>... entityConfigurations );

    /**
     * Lookup the entity configuration for a given class.
     * @param entityClass The class of the entity configuration to retrieve
     * @return Entity configuration for a given class
     */
    <T> YogaEntityConfiguration<T> getEntityConfiguration( Class<T> entityClass );
}
