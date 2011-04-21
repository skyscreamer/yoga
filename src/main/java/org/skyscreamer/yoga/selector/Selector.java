package org.skyscreamer.yoga.selector;

import java.util.HashSet;
import java.util.Set;

public class Selector
{
    private Set<SelectorField> _fields = new HashSet<SelectorField>();

    public Set<SelectorField> getFields()
    {
        return _fields;
    }

    public Selector( String selectorExpression ) throws ParseSelectorException
    {
        if ( selectorExpression.equals( ":" ) )
        {
            return;
        }

        if ( !selectorExpression.startsWith( ":(" ) )
        {
            throw new ParseSelectorException( "Selector must start with ':('" );
        }

        StringBuilder selector = new StringBuilder( selectorExpression );
        int matchIndex = getMatchingParenthesesIndex( selector, 1 );

        selector.delete( matchIndex, selector.length() );
        selector.delete( 0, 2 );

        while ( selector.length() > 0 )
        {
            SelectorField selectorField = getNextSelectorField( selector );
            _fields.add( selectorField );
        }
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

    private SelectorField getNextSelectorField( StringBuilder selector ) throws ParseSelectorException
    {
        int index = 0;
        boolean done = false;
        StringBuilder fieldName = new StringBuilder();
        Selector subSelector = new Selector( ":" );

        while ( !done )
        {
            if ( selector.charAt( index ) == ',' )
            {
                done = true;
            }
            else if ( selector.charAt( index ) == ':' )
            {
                done = true;
                int matchIndex = getMatchingParenthesesIndex( selector, index + 1 );
                subSelector = new Selector( selector.substring( index, matchIndex + 1 ) );

                if ( selector.length() > matchIndex + 1 && selector.charAt( matchIndex + 1 ) != ',' )
                {
                    throw new ParseSelectorException();  // TODO: Add informative message here
                }
                index = matchIndex + 1;
            }
            else
            {
                fieldName.append( selector.charAt( index ) );
            }

            index++;
            if ( index == selector.length() )
            {
                done = true;
            }
        }

        selector.delete( 0, index );
        return new SelectorField( fieldName.toString(), subSelector );
    }
}

