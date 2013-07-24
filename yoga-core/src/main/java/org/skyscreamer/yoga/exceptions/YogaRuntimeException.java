package org.skyscreamer.yoga.exceptions;

/**
 * This is the root of all yoga exceptions
 *
 */
public class YogaRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = -4019301701247046285L;

    public YogaRuntimeException()
    {
        super();
    }

    public YogaRuntimeException( String arg0, Throwable arg1 )
    {
        super( arg0, arg1 );
    }

    public YogaRuntimeException( String arg0 )
    {
        super( arg0 );
    }

    public YogaRuntimeException( Throwable arg0 )
    {
        super( arg0 );
    }


}
