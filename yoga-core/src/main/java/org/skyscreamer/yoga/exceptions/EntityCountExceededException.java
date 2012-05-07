package org.skyscreamer.yoga.exceptions;


/**
 * This exception is thrown when the maximum number of entities for a request is exceeded.
 *
 * @see org.skyscreamer.yoga.model.HierarchicalModelEntityCounter
 * @author Carter Page <carter@skyscreamer.org>
 */
@SuppressWarnings("serial")
public class EntityCountExceededException extends YogaRuntimeException
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
