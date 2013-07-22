package org.skyscreamer.yoga.selector.parser;

import java.util.Map;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;

/**
 * An implementation of {@link AliasSelectorResolver} that uses an injected Map to translate the alias. The key of each
 * map entry is the alias, and the value is the fully-formed selector expression.
 */
public class MapSelectorResolver implements AliasSelectorResolver
{
    private Map<String, String> _definedSelectors;

    public String resolveSelector( String aliasSelectorExpression ) throws ParseSelectorException
    {
        String result = _definedSelectors.get( aliasSelectorExpression );
        if ( result == null )
        {
            throw new ParseSelectorException( "No selector defined for " + aliasSelectorExpression );
        }

        return result;
    }

    /**
     * Injects the map that will be used to resolve selector expressions
     *
     * @param definedSelectors Your map of aliases
     */
    public void setDefinedSelectors( Map<String, String> definedSelectors )
    {
        _definedSelectors = definedSelectors;
    }
}
