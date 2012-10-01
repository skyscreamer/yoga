package org.skyscreamer.yoga.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is used to identify a method in a field populator that lists the "core" fields for the entity handled.
 *
 * The method that is annotated should return a Collection of Strings that list the fields to be treated as core (which
 * is to say, they are the default fields returned).
 *
 * If a CoreFields annotation is present in a field populator, it overrides the @Core annotation in the entity itself.
 * If it is absent, the @Core annotation will be used to identify the core fields.
 *
 * @see PopulationExtension
 * @see Core
 * @author Corby Page
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CoreFields
{
}
