package org.skyscreamer.yoga.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * The RenderingListenerRegistry allows you to declare one or more objects that implement the
 * {@link org.skyscreamer.yoga.listener.RenderingListener} interface. These Rendering Listeners will process or
 * decorate the response from a Yoga call before it is returned to the user.
 *
 * <p> The RenderingListener objects are stored in an ordered collection, and will be processed in sequence for
 * each Yoga call.</p>
 *
 * @see RenderingListener
 */
public class RenderingListenerRegistry
{
    protected Collection<RenderingListener> listeners;

    /**
     * Constructor to initialize the registry with a set of 0 or more listeners
     *
     * @param listeners The ordered collection of rendering listeners
     */
    public RenderingListenerRegistry( RenderingListener... listeners )
    {
        this.listeners = new ArrayList<RenderingListener>( Arrays.asList( listeners ) );
    }

    /**
     * Constructor to initialize the registry with a set of listeners
     *
     * @param listeners The ordered collection of rendering listeners
     */
    public RenderingListenerRegistry( Collection<RenderingListener> listeners )
    {
        this.listeners = listeners;
    }

    /**
     * Retrieves the ordered collection of rendering listeners used by the registry
     *
     * @return The rendering listeners
     */
    public Collection<RenderingListener> getListeners()
    {
        return Collections.unmodifiableCollection( listeners );
    }

    /**
     * Adds a rendering listener to the end of the ordered collection used by the registry
     *
     * @param listener The rendering listener to add
     */
    public void addListener( RenderingListener listener )
    {
        listeners.add( listener );
    }

    /**
     * Replaces the ordered collection of rendering listeners used by the registry
     *
     * @param listeners The new collection of rendering listeners
     */
    public void setListeners( Collection<RenderingListener> listeners )
    {
        this.listeners = listeners;
    }
}
