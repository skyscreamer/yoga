package org.skyscreamer.yoga.mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingListener;

public class YogaRequestContext
{
    private final String urlSuffix;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final Collection<RenderingListener> listeners;

    public YogaRequestContext( String urlSuffix, HttpServletRequest request,
            HttpServletResponse response, RenderingListener... listeners )
    {
        this( urlSuffix, request, response, Arrays.asList( listeners ) );
    }

    public YogaRequestContext( String urlSuffix, HttpServletRequest request,
            HttpServletResponse response, Collection<RenderingListener> listeners )
    {
        this.urlSuffix = urlSuffix;
        this.request = request;
        this.response = response;
        this.listeners = listeners;
    }

    public String getUrlSuffix()
    {
        return urlSuffix;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setProperty( String key, Object value )
    {
        properties.put( key, value );
    }

    public Object getProperty( String key )
    {
        return properties.get( key );
    }

    public Collection<RenderingListener> getListeners()
    {
        return listeners;
    }

    public void emitEvent( RenderingEvent event )
    {
        if (listeners == null)
        {
            return;
        }
        for (RenderingListener renderingListener : listeners)
        {
            renderingListener.eventOccurred( event );
        }
    }
}
