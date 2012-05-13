package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.FieldSelector;

/**
 * <p>
 * GData-style SelectorParser. Parses selectors of the style:
 * </p>
 * <code>
 * favoriteArtists,friends(favoriteArtists(albums))
 * </code>
 * 
 * @author Carter Page <carter@skyscreamer.org>
 */
public class GDataSelectorParser extends ParentheticalSelectorParser
{
    @Override
    protected FieldSelector parse( String selectorExpression ) throws ParseSelectorException
    {
        return parseParentheticalSelector( selectorExpression, "(" );
    }
}
