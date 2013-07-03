package org.skyscreamer.yoga.jaxrs.view;

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
import org.skyscreamer.yoga.selector.MapSelector;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.view.AbstractYogaView;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSelectorMessageBodyWriter implements MessageBodyWriter<Object>
{
    protected ResultTraverser _resultTraverser = new ResultTraverser();

    @Autowired
    protected SelectorParser _selectorParser;

    @Autowired
    protected RenderingListenerRegistry _renderingListenerRegistry = new RenderingListenerRegistry();

    @Autowired
    protected MapSelector _selector;
    
    @Context
    protected HttpServletRequest _request;

    @Context
    protected HttpServletResponse _response;

	protected ClassFinderStrategy _classFinderStrategy;

    @Autowired
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        this._classFinderStrategy = classFinderStrategy;
        _resultTraverser.setClassFinderStrategy( classFinderStrategy );
    }

    
    @Override
    public long getSize( Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4 )
    {
        return -1;
    }

    @Override
    public boolean isWriteable( Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3 )
    {
        return true;
    }

    @Override
    public void writeTo( Object t, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream ) throws IOException, WebApplicationException
    {
        try
        {
            AbstractYogaView view = getView();
            view.setResultTraverser( _resultTraverser );
            view.setSelectorParser( _selectorParser );
            view.setSelector( _selector );
            view.setRegistry( _renderingListenerRegistry );
            view.render( _request, _response, t, entityStream );
            if ( _classFinderStrategy != null )
            {
                view.setClassFinderStrategy( _classFinderStrategy );
            }
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new WebApplicationException( e, Response.Status.BAD_REQUEST );
        }
    }

    protected abstract AbstractYogaView getView();
}
