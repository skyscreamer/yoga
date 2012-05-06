package org.skyscreamer.yoga.populator;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public interface FieldPopulatorRegistry
{
    FieldPopulator getFieldPopulator(Class<?> clazz);
}
