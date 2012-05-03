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
        int matchIndex = getMatchingParenthesisIndex(stringBuilder, 1);

        stringBuilder.delete( matchIndex, stringBuilder.length() );
        stringBuilder.delete( 0, 2 );

        while ( stringBuilder.length() > 0 )
        {
            processNextSelectorField( selector, stringBuilder );
        }
        return selector;
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
                int matchIndex = getMatchingParenthesisIndex(selectorBuff, index + 1);
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
}
