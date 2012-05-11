package org.skyscreamer.yoga.populator;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public interface FieldPopulatorRegistry
{
    Object getFieldPopulator( Class<?> clazz );
}
