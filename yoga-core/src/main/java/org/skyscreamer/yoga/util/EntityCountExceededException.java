package org.skyscreamer.yoga.util;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 3/31/12 Time: 5:26 PM
 */
@SuppressWarnings("serial")
public class EntityCountExceededException extends RuntimeException
{
    public EntityCountExceededException()
    {
        super();
    }

    public EntityCountExceededException(String s)
    {
        super( s );
    }
}
