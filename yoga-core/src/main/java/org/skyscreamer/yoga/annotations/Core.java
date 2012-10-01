package org.skyscreamer.yoga.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a the field which corresponds to an annotated getter is a "core" field for an entity.
 *
 * Core fields are always returned by default and do not need to be specified in a selector.
 *
 * @author Corby Page
 * @author Solomon Duskis
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Core
{
}
