package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.util.ParenthesisUtil;

/**
 * <p>
 * LinkedIn style selector parser. Parses selectors like the following:
 * </p>
 * <code>
 * :(favoriteArtists,friends:(favoriteArtists:(albums)))
 * </code>
 *
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 * @see <a href="http://blog.linkedin.com/2009/07/08/brandon-duncan-java-one-building-consistent-restful-apis-in-a-high-performance-environment/">Brandon Duncan's Presentation</a>
 */
public class LinkedInSelectorParser extends ParentheticalSelectorParser
{
    public static final String SELECTOR_TYPE = "LinkedIn";

    private static final String LINKEDIN_SELECTOR_JS_URL = "/js/selector.js";

    private static final String EXPLICIT_SELECTOR_PREFIX = ":(";


    @Override
    public String getSelectorJavascriptURL() {
        return LINKEDIN_SELECTOR_JS_URL;
    }

    @Override
    public Object getSelectorType() {
        return SELECTOR_TYPE;
    }

    protected FieldSelector parse( String selectorExpression ) throws ParseSelectorException
    {
        if (selectorExpression.equals( ":" ))
        {
            return new FieldSelector();
        }

        if (!selectorExpression.startsWith( EXPLICIT_SELECTOR_PREFIX ))
        {
            String message = "Selector must start with " + EXPLICIT_SELECTOR_PREFIX;
            throw new ParseSelectorException( message );
        }

        if ( ParenthesisUtil.getMatchingParenthesisIndex( selectorExpression, 1 ) != (selectorExpression.length() - 1) )
        {
            throw new ParseSelectorException( "Selector must end with a parenthesis" );
        }

        String rawSelectorExpression = selectorExpression.substring( 2, selectorExpression.length() - 1 );
        String openParenthesis = ":(";

        return parseParentheticalSelector( rawSelectorExpression, openParenthesis );
    }
}
