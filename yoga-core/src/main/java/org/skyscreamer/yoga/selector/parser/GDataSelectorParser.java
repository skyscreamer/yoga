package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
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
    public GDataSelectorParser( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        super( fieldPopulatorRegistry );
    }

    @Override
    protected FieldSelector parse( String selectorExpression ) throws ParseSelectorException
    {
        return parseParentheticalSelector( selectorExpression, "(" );
    }

}
