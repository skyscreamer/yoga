package org.skyscreamer.yoga.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RenderingListenerRegistry
{
    protected Collection<RenderingListener> listeners;

    public RenderingListenerRegistry()
    {
        this(new ArrayList<RenderingListener>());
    }

    public RenderingListenerRegistry( Collection<RenderingListener> listeners )
    {
        this.listeners = listeners;
    }

    public Collection<RenderingListener> getListeners()
    {
        return Collections.unmodifiableCollection( listeners );
    }

    public void addListner( RenderingListener listener )
    {
        listeners.add( listener );
    }

    public void setListeners( Collection<RenderingListener> listeners )
    {
        this.listeners = listeners;
    }

}
