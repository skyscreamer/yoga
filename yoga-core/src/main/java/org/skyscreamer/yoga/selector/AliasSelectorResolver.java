package org.skyscreamer.yoga.selector;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public interface AliasSelectorResolver
{
    String resolveSelector(String aliasSelectorExpression) throws ParseSelectorException;
}
