package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.util.ParenthesisUtil;

/**
 * This abstract class provides functionality for parenthetical-style selector
 * parsers.
 * 
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 */
public abstract class ParentheticalSelectorParser extends SelectorParser
{
    protected FieldSelector parseParentheticalSelector( String rawSelectorExpression, String openParenthesis ) throws ParseSelectorException
    {
        FieldSelector selector = new FieldSelector();
        StringBuilder stringBuilder = new StringBuilder( rawSelectorExpression );
        while (stringBuilder.length() > 0)
        {
            processNextSelectorField( selector, stringBuilder, openParenthesis );
        }
        return selector;
    }

    private void processNextSelectorField(FieldSelector selector, StringBuilder selectorBuff,
            String openParenthesis) throws ParseSelectorException
    {
        int index = 0;
        boolean done = false;
        StringBuilder fieldNameBuilder = new StringBuilder();
        FieldSelector subSelector = new FieldSelector();

        while (!done)
        {
            if (selectorBuff.charAt( index ) == ',')
            {
                done = true;
            }
            else if ( selectorBuff.substring( index ).startsWith( openParenthesis ) )
            {
                done = true;
                int matchIndex = ParenthesisUtil.getMatchingParenthesisIndex( selectorBuff, index
                        + openParenthesis.length() - 1 );
                subSelector = parseParentheticalSelector(
                        selectorBuff.substring( index + openParenthesis.length(), matchIndex ),
                        openParenthesis );

                if (selectorBuff.length() > matchIndex + 1
                        && selectorBuff.charAt( matchIndex + 1 ) != ',')
                {
                    throw new ParseSelectorException( "A nested selector not at the end of its parent must be followed by a comma" );
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
        selector.register( fieldName, subSelector );
    }
}
