package org.skyscreamer.yoga.util;

/**
 * This exception is thrown when the maximum number of entities for a request is exceeded.
 *
 * @see org.skyscreamer.yoga.mapper.HierarchicalModelEntityCounter
 * @author Carter Page <carter@skyscreamer.org>
 */
public class EntityCountExceededException extends RuntimeException {
    public EntityCountExceededException() {
        super();
    }

    public EntityCountExceededException(String s) {
        super(s);
    }
}
