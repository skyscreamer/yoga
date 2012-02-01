package org.skyscreamer.yoga.selector;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public interface PredefinedSelectorResolver
{
    String resolveSelector( String predefinedSelectorExpression ) throws ParseSelectorException;
}
