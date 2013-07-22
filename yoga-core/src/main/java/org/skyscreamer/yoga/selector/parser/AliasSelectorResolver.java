package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;

/**
 * An AliasSelectorResolver allows you to define meaningful aliases for a given selector expression. In a production
 * environment, you will probably want to disable the ability to query through arbitrary selector, and only allow
 * access to your system through defined aliases.
 *
 * Inject an implementation of AliasSelectorResolver into your {@link SelectorParser} to use it in your application.
 * Yoga provides default implementations for defining aliases through property files or configured maps, but defining
 * your own is as simple as implementing the interface.
 *
 * @see DynamicPropertyResolver
 * @see MapSelectorResolver
 */
public interface AliasSelectorResolver
{
    /**
     * The method that translates an alias into a fully-formed selector expression
     *
     * @param aliasSelectorExpression the alias expression passed in on the URI
     * @return the complete selector expression represented by the alias
     * @throws ParseSelectorException thrown if the input alias expression is not valid
     */
    String resolveSelector( String aliasSelectorExpression ) throws ParseSelectorException;
}
