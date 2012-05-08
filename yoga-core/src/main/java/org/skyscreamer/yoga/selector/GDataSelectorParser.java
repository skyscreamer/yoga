package org.skyscreamer.yoga.selector;

/**
 * <p>GData-style SelectorParser.  Parses selectors of the style:</p>
 * <code>
 * favoriteArtists,friends(favoriteArtists(albums))
 * </code>
 *
 * @author Carter Page <carter@skyscreamer.org>
 */
public class GDataSelectorParser extends ParentheticalSelectorParser
{
    @Override
    protected Selector parse( String selectorExpression ) throws ParseSelectorException
    {
        return parseParentheticalSelector( selectorExpression, "(" );
    }
}
