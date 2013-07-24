package org.skyscreamer.yoga.exceptions;


/**
 * This exception is thrown when the maximum number of entities for a request is exceeded.
 *
 * @author Carter Page <carter@skyscreamer.org>
 * @see org.skyscreamer.yoga.mapper.HierarchicalModelEntityCounter
 */
@SuppressWarnings("serial")
public class EntityCountExceededException extends YogaRuntimeException
{
    public EntityCountExceededException()
    {
        super();
    }

    public EntityCountExceededException( String s )
    {
        super( s );
    }
}
