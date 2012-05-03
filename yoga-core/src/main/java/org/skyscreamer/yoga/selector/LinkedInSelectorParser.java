package org.skyscreamer.yoga.selector;

/**
 * LinkedIn style selector parser
 *
 * @see <a href="http://blog.linkedin.com/2009/07/08/brandon-duncan-java-one-building-consistent-restful-apis-in-a-high-performance-environment/">Brandon Duncan's Presentation</a>
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 */
public class LinkedInSelectorParser extends SelectorParser {
    private static final String EXPLICIT_SELECTOR_PREFIX = ":(";
    private static final String ALIAS_SELECTOR_PREFIX = "$";

    private AliasSelectorResolver _aliasSelectorResolver;
    private boolean _disableExplicitSelectors = false;

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

    public DefinedSelectorImpl parse( String selectorExpression ) throws ParseSelectorException
    {
        DefinedSelectorImpl selector = new DefinedSelectorImpl();
        if ( selectorExpression.equals( ":" ) )
        {
            return selector;
        }

        if ( selectorExpression.startsWith( EXPLICIT_SELECTOR_PREFIX ) && _disableExplicitSelectors )
        {
            throw new ParseSelectorException( "Explicit selectors have been disabled" );
        }

        if ( selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            selectorExpression = _aliasSelectorResolver.resolveSelector( selectorExpression );
        }

        if ( !selectorExpression.startsWith( EXPLICIT_SELECTOR_PREFIX ) )
        {
            String message = "Selector must start with " + ALIAS_SELECTOR_PREFIX;
            if ( !_disableExplicitSelectors )
            {
                message += " or " + EXPLICIT_SELECTOR_PREFIX;
            }
            throw new ParseSelectorException( message );
        }

        StringBuilder stringBuilder = new StringBuilder( selectorExpression );
        int matchIndex = getMatchingParenthesesIndex( stringBuilder, 1 );

        stringBuilder.delete( matchIndex, stringBuilder.length() );
        stringBuilder.delete( 0, 2 );

        while ( stringBuilder.length() > 0 )
        {
            processNextSelectorField( selector, stringBuilder );
        }
        return selector;
    }

    private int getMatchingParenthesesIndex( StringBuilder selector, int index ) throws ParseSelectorException
    {
        int parenthesesCount = 1;
        while ( parenthesesCount > 0 && index < selector.length() - 1 )
        {
            index++;
            if ( selector.charAt( index ) == '(' )
            {
                parenthesesCount++;
            }
            if ( selector.charAt( index ) == ')' )
            {
                parenthesesCount--;
            }
        }

        if ( parenthesesCount > 0 )
        {
            throw new ParseSelectorException( "More opening parentheses than closing parentheses" );
        }
        return index;
    }

    private void processNextSelectorField( DefinedSelectorImpl selector, StringBuilder selectorBuff )
            throws ParseSelectorException
    {
        int index = 0;
        boolean done = false;
        StringBuilder fieldNameBuilder = new StringBuilder();
        DefinedSelectorImpl subSelector = new DefinedSelectorImpl();

        while ( !done )
        {
            if ( selectorBuff.charAt( index ) == ',' )
            {
                done = true;
            }
            else if ( selectorBuff.charAt( index ) == ':' )
            {
                done = true;
                int matchIndex = getMatchingParenthesesIndex( selectorBuff, index + 1 );
                subSelector = parse( selectorBuff.substring( index, matchIndex + 1 ) );

                if ( selectorBuff.length() > matchIndex + 1 && selectorBuff.charAt( matchIndex + 1 ) != ',' )
                {
                    throw new ParseSelectorException();  // TODO: Add informative message here
                }
                index = matchIndex + 1;
            }
            else
            {
                fieldNameBuilder.append( selectorBuff.charAt( index ) );
            }

            index++;
            if ( index == selectorBuff.length() )
            {
                done = true;
            }
        }

        selectorBuff.delete( 0, index );
        String fieldName = fieldNameBuilder.toString();
        if ( fieldName.equals( HREF ) )
        {
            throw new IllegalArgumentException( HREF + " is a reserved keyword for selectors" );
        }
        selector._fields.put( fieldName, subSelector);
    }

    public void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver )
    {
        _aliasSelectorResolver = aliasSelectorResolver;
    }

    public void setDisableExplicitSelectors( boolean disableExplicitSelectors )
    {
        _disableExplicitSelectors = disableExplicitSelectors;
    }
}
