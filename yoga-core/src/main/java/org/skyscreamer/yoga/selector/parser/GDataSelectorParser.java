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
    private static final String GDATA_SELECTOR_JS_URL = "/js/selector-gdata.js";
    public static final String SELECTOR_TYPE = "GData";

    @Override
    protected FieldSelector parse( String selectorExpression ) throws ParseSelectorException
    {
        return parseParentheticalSelector( selectorExpression, "(" );
    }

    @Override
    public String getSelectorJavascriptURL() {
        return GDATA_SELECTOR_JS_URL;
    }

    @Override
    public Object getSelectorType() {
        return SELECTOR_TYPE;
    }
}
