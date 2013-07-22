package org.skyscreamer.yoga.util;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;

/**
 * Utility for finding nested parentheses
 *
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 */
public class ParenthesisUtil
{
    /**
     * Given an index of an opening parenthesis "(", find the matching closing parenthesis ")".  If there are embedded
     * parenthesis inside the matching pair, this method will ignore them.
     * <p/>
     * Example:
     * <pre>
     *      getMatchingParenthesis("a(b(c))", 1) returns 6
     *      getMatchingParenthesis("a(b(c))", 3) returns 5
     * </pre>
     *
     * @param selector String to analyze for the matching selector
     * @param index    Index of the opening parenthesis to match
     * @return Index of matching closing parenthesis
     * @throws org.skyscreamer.yoga.exceptions.ParseSelectorException
     */
    public static int getMatchingParenthesisIndex( CharSequence selector, int index ) throws ParseSelectorException
    {
        return getMatchingBracketIndex( selector, index, '(', ')' );
    }

    private static int getMatchingBracketIndex( CharSequence selector, int index, char openBracket, char closeBracket )
            throws ParseSelectorException
    {
        if ( selector.charAt( index ) != openBracket )
        {
            throw new ParseSelectorException( "Selector does not have an opening bracket at index " + index );
        }
        int parenthesesCount = 1;
        while ( parenthesesCount > 0 && index < selector.length() - 1 )
        {
            index++;
            if ( selector.charAt( index ) == openBracket )
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
