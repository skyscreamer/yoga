package org.skyscreamer.yoga.exceptions;

/**
 * This exception is thrown when the selector can't be parsed
 *
 * @author Carter Page <carter@skyscreamer.org>
 * @see org.skyscreamer.yoga.selector.parser.SelectorParser
 */

public class ParseSelectorException extends YogaRuntimeException
{
    private static final long serialVersionUID = -5307328628794516457L;

    public ParseSelectorException( String s )
    {
        super( s );
    }
}
