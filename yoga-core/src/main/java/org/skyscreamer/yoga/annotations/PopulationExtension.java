package org.skyscreamer.yoga.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a class that is a field populator.
 *
 * The value of the annotation should be the class that is being populated.
 *
 * @author Corby Page
 * @see CoreFields
 * @see SupportedFields
 * @see ExtraField
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PopulationExtension
{
    Class<?> value();
}
