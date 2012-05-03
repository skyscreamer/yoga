package org.skyscreamer.yoga.selector;

/**
 * A SelectorParser takes a string selector argument and translates it into a Selector which is used to navigate
 * the object tree and build results.
 *
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 */
public abstract class SelectorParser
{
    public static final String HREF = "href";
    public static final String DEFINITION = "definition";

    protected AliasSelectorResolver _aliasSelectorResolver;
    protected boolean _disableExplicitSelectors = false;

    public abstract Selector parse( String selectorExpression ) throws ParseSelectorException;

    public Selector parseSelector( String selectorStr )
    {
        Selector selector = new CoreSelector();
        if ( selectorStr != null )
        {
            try
            {
                selector = new CombinedSelector( selector, parse( selectorStr ) );
            }
            catch ( ParseSelectorException e )
            {
                // TODO: Add logging here.
                throw new IllegalArgumentException( "Could not parse selector", e );
            }
        }
        return selector;
    }

    public void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver )
    {
        _aliasSelectorResolver = aliasSelectorResolver;
    }

    public void setDisableExplicitSelectors( boolean disableExplicitSelectors )
    {
        _disableExplicitSelectors = disableExplicitSelectors;
    }

    /**
     * Given an index of an opening parenthesis "(", find the matching closing parenthesis ")".  If there are embedded
     * parenthesis inside the matching pair, this method will ignore them.
     *
     * Example:
     * <pre>
     *      getMatchingParenthesis("a(b(c))", 1) returns 6
     *      getMatchingParenthesis("a(b(c))", 3) returns 5
     * </pre>
     *
     * @param selector String to analyze for the matching selector
     * @param index Index of the opening parenthesis to match
     * @return Index of matching closing parenthesis
     * @throws ParseSelectorException
     */
    protected int getMatchingParenthesisIndex(CharSequence selector, int index) throws ParseSelectorException
    {
        return getMatchingBracketIndex(selector, index, '(', ')');
    }

    private int getMatchingBracketIndex(CharSequence selector, int index, char openBracket, char closeBracket)
            throws ParseSelectorException
    {
        if (selector.charAt( index ) != openBracket)
        {
            throw new ParseSelectorException( "Selector does not have an opening bracket at index " + index);
        }
        int parenthesesCount = 1;
        while ( parenthesesCount > 0 && index < selector.length() - 1 )
        {
            index++;
            if ( selector.charAt( index ) == openBracket)
            {
                parenthesesCount++;
            }
            if ( selector.charAt( index ) == closeBracket )
            {
                parenthesesCount--;
            }
        }

        if ( parenthesesCount > 0 )
        {
            throw new ParseSelectorException( "More opening brackets than closing brackets" );
        }
        return index;
    }
}
