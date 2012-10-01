package org.skyscreamer.yoga.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExtraField is used to identify a method in a field populator to return an additional field in an entity request.
 * The field name is specified by the annotation's value.
 *
 * A common use case is to use it to add a calculated field to a returned entity.
 *
 * @author Corby Page
 * @see PopulationExtension
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ExtraField
{
    String value();
}
