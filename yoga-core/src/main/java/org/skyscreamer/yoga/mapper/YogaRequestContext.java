package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class YogaRequestContext
{
    private final String urlSuffix;
    private final SelectorParser selectorParser;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final Collection<RenderingListener> listeners;

    public YogaRequestContext( String urlSuffix, SelectorParser parser, HttpServletRequest request,
            HttpServletResponse response, RenderingListener... listeners )
    {
        this( urlSuffix, parser, request, response, Arrays.asList( listeners ) );
    }

    public YogaRequestContext( String urlSuffix, SelectorParser parser, HttpServletRequest request,
            HttpServletResponse response, Collection<RenderingListener> listeners )
    {
        this.urlSuffix = urlSuffix;
        this.selectorParser = parser;
        this.request = request;
        this.response = response;
        this.listeners = listeners;
    }

    public String getUrlSuffix()
    {
        return urlSuffix;
    }

    public SelectorParser getSelectorParser() {
        return selectorParser;
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

    public void emitEvent( RenderingEventType eventType, HierarchicalModel<?> model, Object value,
            Class<?> valueType, YogaRequestContext context, Selector selector )
    {
        emitEvent( new RenderingEvent( eventType, model, value, valueType, context, selector ) );
    }
}
