package org.skyscreamer.yoga.listener;

import java.io.IOException;

/**
 * A Rendering Listener is an object that contains rules for processing or decorating the output from a Yoga call
 * before it is returned to the user. Any rendering listener that lives in an application's
 * {@link RenderingListenerRegistry} will be invoked after a Yoga invocation.
 *
 * @see RenderingListenerRegistry
 */
public interface RenderingListener
{
    /**
     * The eventOccurred method is invoked by Yoga on each RenderingListener in the application's registry
     * before the response data is returned to the user
     *
     * @param event The RenderingEvent object provides the RenderingListener with context information about the
     *              Yoga call, including the HTTP request and response, the selectors used, and the response data
     * @throws IOException 
     */
    <T> void eventOccurred( RenderingEvent<T> event ) throws IOException;
}
