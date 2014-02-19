package org.skyscreamer.yoga.mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class YogaRequestContext
{
    private final String urlSuffix;
    private final SelectorResolver selectorResolver;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private final Collection<RenderingListener> listeners;

    public YogaRequestContext( String urlSuffix, SelectorResolver resolver, HttpServletRequest request,
            HttpServletResponse response, RenderingListener... listeners )
    {
        this( urlSuffix, resolver, request, response, Arrays.asList( listeners ) );
    }

    public YogaRequestContext( String urlSuffix, SelectorResolver resolver, HttpServletRequest request,
            HttpServletResponse response, Collection<RenderingListener> listeners )
    {
        this.urlSuffix = urlSuffix;
        this.selectorResolver = resolver;
        this.request = request;
        this.response = response;
        this.listeners = listeners;
    }

    public String getUrlSuffix()
    {
        return urlSuffix;
    }

    public SelectorParser getSelectorParser() 
    {
        return this.selectorResolver.getSelectorParser();
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

    public <T> void emitEvent( RenderingEvent<T> event ) throws IOException
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

    public <T> void emitEvent(MapHierarchicalModel<?> model, T value,
            Class<T> type, YogaRequestContext context, Selector selector)
            throws IOException
    {
        emitEvent(new RenderingEvent<T>(RenderingEventType.POJO_CHILD, model,
                value, type, context, selector));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void emitEvent(ListHierarchicalModel<?> model, Iterable<?> iterable,
            YogaRequestContext context, Selector selector) throws IOException
    {
        emitEvent(new RenderingEvent(RenderingEventType.LIST_CHILD, model,
                iterable, iterable.getClass(), context, selector));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void emitEvent(MapHierarchicalModel<?> model, Map<?,?> value,
        YogaRequestContext context, Selector selector) throws IOException
    {
        emitEvent(new RenderingEvent(RenderingEventType.MAP_CHILD, model,
                value, Map.class, context, selector));
    }
    
    public Selector getSelector()
    {
        return this.selectorResolver.getSelector( request );
    }
}
