package org.skyscreamer.yoga.selector;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public class MapSelectorResolver implements PredefinedSelectorResolver
{
    private Map<String,String> _definedSelectors;

    public String resolveSelector( String predefinedSelectorExpression ) throws ParseSelectorException
    {
        String result = _definedSelectors.get( predefinedSelectorExpression );
        if ( result == null )
        {
            throw new ParseSelectorException( "No selector defined for " + predefinedSelectorExpression );
        }

        return result;
    }

    public void setDefinedSelectors( Map<String, String> definedSelectors )
    {
        _definedSelectors = definedSelectors;
    }
}
