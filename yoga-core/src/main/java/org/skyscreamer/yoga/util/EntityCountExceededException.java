package org.skyscreamer.yoga.util;

/**
<<<<<<< HEAD
 * Created by IntelliJ IDEA. User: Carter Page Date: 3/31/12 Time: 5:26 PM
=======
 * This exception is thrown when the maximum number of entities for a request is exceeded.
 *
 * @see org.skyscreamer.yoga.mapper.HierarchicalModelEntityCounter
 * @author Carter Page <carter@skyscreamer.org>
>>>>>>> upstream/master
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
