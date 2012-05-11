package org.skyscreamer.yoga.selector.parser;

import java.util.Map;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;

/**
 * Created by IntelliJ IDEA. User: corby
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

    public void setDefinedSelectors( Map<String, String> definedSelectors )
    {
        _definedSelectors = definedSelectors;
    }
}
