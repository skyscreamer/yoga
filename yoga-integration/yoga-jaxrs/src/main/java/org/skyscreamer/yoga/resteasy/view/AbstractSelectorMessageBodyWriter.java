package org.skyscreamer.yoga.resteasy.view;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;

import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.view.AbstractYogaView;

public abstract class AbstractSelectorMessageBodyWriter implements MessageBodyWriter<Object>
{
    protected ResultTraverser _resultTraverser;

    protected SelectorParser _selectorParser;

    protected RenderingListenerRegistry _registry;

    @Context
    HttpServletRequest _request;

    @Context
    HttpServletResponse response;

    @Override
    public long getSize(Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4)
    {
        return -1;
    }

    @Override
    public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3)
    {
        return true;
    }

    @Override
    public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream) throws IOException, WebApplicationException
    {
        try
        {
            AbstractYogaView view = getView();
            view.setResultTraverser( _resultTraverser );
            view.setSelectorParser( _selectorParser );
            Selector selector = view.getSelector( _request );
            YogaRequestContext context = new YogaRequestContext( view.getHrefSuffix(), _request, response, _registry.getListeners() );
            view.render( selector, t, context );
        } catch ( RuntimeException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            throw new WebApplicationException( e, Response.Status.BAD_REQUEST );
        }
    }

    protected abstract AbstractYogaView getView();

}
