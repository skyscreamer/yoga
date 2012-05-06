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
    public static final String ALIAS_SELECTOR_PREFIX = "$";
    public static final String HREF = "href";
    public static final String DEFINITION = "definition";

    protected AliasSelectorResolver _aliasSelectorResolver;
    protected boolean _disableExplicitSelectors = false;

    public abstract Selector parse( String selectorExpression ) throws ParseSelectorException;

<<<<<<< HEAD
    public Selector parseSelector(String selectorStr)
    {
        Selector selector = new CoreSelector();
        if (selectorStr != null)
        {
            try
            {
                selector = new CombinedSelector( selector, parse( selectorStr ) );
            }
            catch (ParseSelectorException e)
            {
                // TODO: Add logging here.
                throw new IllegalArgumentException( "Could not parse selector", e );
            }
        }
        return selector;
    }

    public DefinedSelectorImpl parse(String selectorExpression) throws ParseSelectorException
    {
        DefinedSelectorImpl selector = new DefinedSelectorImpl();
        if (selectorExpression.equals( ":" ))
        {
            return selector;
        }

        if (selectorExpression.startsWith( EXPLICIT_SELECTOR_PREFIX ) && _disableExplicitSelectors)
=======
    public Selector parseSelector( String selectorExpression ) throws ParseSelectorException {
        if ( _disableExplicitSelectors && !selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
>>>>>>> upstream/master
        {
            throw new ParseSelectorException( "Explicit selectors have been disabled" );
        }

        if (selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ))
        {
            selectorExpression = _aliasSelectorResolver.resolveSelector( selectorExpression );
        }

<<<<<<< HEAD
        if (!selectorExpression.startsWith( EXPLICIT_SELECTOR_PREFIX ))
        {
            String message = "Selector must start with " + ALIAS_SELECTOR_PREFIX;
            if (!_disableExplicitSelectors)
            {
                message += " or " + EXPLICIT_SELECTOR_PREFIX;
            }
            throw new ParseSelectorException( message );
        }

        StringBuilder stringBuilder = new StringBuilder( selectorExpression );
        int matchIndex = getMatchingParenthesesIndex( stringBuilder, 1 );

        stringBuilder.delete( matchIndex, stringBuilder.length() );
        stringBuilder.delete( 0, 2 );

        while (stringBuilder.length() > 0)
=======
        Selector selector = new CoreSelector();
        if ( selectorExpression != null )
>>>>>>> upstream/master
        {
            selector = new CombinedSelector( selector, parse( selectorExpression ) );
        }
        return selector;
    }

<<<<<<< HEAD
    private int getMatchingParenthesesIndex(StringBuilder selector, int index)
            throws ParseSelectorException
    {
        int parenthesesCount = 1;
        while (parenthesesCount > 0 && index < selector.length() - 1)
        {
            index++;
            if (selector.charAt( index ) == '(')
            {
                parenthesesCount++;
            }
            if (selector.charAt( index ) == ')')
            {
                parenthesesCount--;
            }
        }

        if (parenthesesCount > 0)
        {
            throw new ParseSelectorException( "More opening parentheses than closing parentheses" );
        }
        return index;
    }

    private void processNextSelectorField(DefinedSelectorImpl selector, StringBuilder selectorBuff)
            throws ParseSelectorException
    {
        int index = 0;
        boolean done = false;
        StringBuilder fieldNameBuilder = new StringBuilder();
        DefinedSelectorImpl subSelector = new DefinedSelectorImpl();

        while (!done)
        {
            if (selectorBuff.charAt( index ) == ',')
            {
                done = true;
            }
            else if (selectorBuff.charAt( index ) == ':')
            {
                done = true;
                int matchIndex = getMatchingParenthesesIndex( selectorBuff, index + 1 );
                subSelector = parse( selectorBuff.substring( index, matchIndex + 1 ) );

                if (selectorBuff.length() > matchIndex + 1
                        && selectorBuff.charAt( matchIndex + 1 ) != ',')
                {
                    throw new ParseSelectorException(); // TODO: Add informative
                                                        // message here
                }
                index = matchIndex + 1;
            }
            else
            {
                fieldNameBuilder.append( selectorBuff.charAt( index ) );
            }

            index++;
            if (index == selectorBuff.length())
            {
                done = true;
            }
        }

        selectorBuff.delete( 0, index );
        String fieldName = fieldNameBuilder.toString();
        if (fieldName.equals( HREF ))
        {
            throw new IllegalArgumentException( HREF + " is a reserved keyword for selectors" );
        }
        selector._fields.put( fieldName, subSelector );
    }

    public void setAliasSelectorResolver(AliasSelectorResolver aliasSelectorResolver)
=======
    public void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver )
>>>>>>> upstream/master
    {
        _aliasSelectorResolver = aliasSelectorResolver;
    }

    public void setDisableExplicitSelectors(boolean disableExplicitSelectors)
    {
        _disableExplicitSelectors = disableExplicitSelectors;
    }
}
