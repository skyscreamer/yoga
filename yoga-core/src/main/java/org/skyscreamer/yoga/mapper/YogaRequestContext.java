package org.skyscreamer.yoga.mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;

public class YogaRequestContext
{
    private final String urlSuffix;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final FieldPopulatorRegistry _fieldPopulatorRegistry;
    private final Collection<RenderingListener> listeners;

    public YogaRequestContext( String urlSuffix, HttpServletRequest request, HttpServletResponse response,
            FieldPopulatorRegistry fieldPopulatorRegistry, RenderingListener... listeners )
    {
        this( urlSuffix, request, response, fieldPopulatorRegistry, Arrays.asList( listeners ) );
    }

    public YogaRequestContext( String urlSuffix, HttpServletRequest request, HttpServletResponse response,
            FieldPopulatorRegistry fieldPopulatorRegistry, Collection<RenderingListener> listeners )
    {
        this.urlSuffix = urlSuffix;
        this.request = request;
        this.response = response;
        this._fieldPopulatorRegistry = fieldPopulatorRegistry;
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

    public FieldPopulatorRegistry getFieldPopulatorRegistry()
    {
        return _fieldPopulatorRegistry;
    }

    public void setProperty( String key, Object value )
    {
        properties.put( key, value );
    }

    public Object getProperty( String key )
    {
        return properties.get( key );
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
