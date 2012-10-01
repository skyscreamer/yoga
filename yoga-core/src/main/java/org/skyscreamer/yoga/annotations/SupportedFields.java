package org.skyscreamer.yoga.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to identify a method of a field populator that returns a list of fields that are supported
 * by a field selector.  By definition core fields do not need to be included.
 *
 * The method must return a type of Collection<String> listing the field names.  If the method is absent, then all
 * fields (getters) are available to be selected.
 *
 * @see PopulationExtension
 * @author Corby Page
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SupportedFields
{
}
