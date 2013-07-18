package org.skyscreamer.yoga.jaxrs.view;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Resource;
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
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;
import org.skyscreamer.yoga.view.AbstractYogaView;

public abstract class AbstractSelectorMessageBodyWriter implements MessageBodyWriter<Object>
{
    protected ResultTraverser _resultTraverser = new ResultTraverser();

    protected SelectorParser _selectorParser = new GDataSelectorParser();

    protected RenderingListenerRegistry _renderingListenerRegistry = new RenderingListenerRegistry();

    protected CoreSelector _selector = new CoreSelector();
    
    protected ClassFinderStrategy _classFinderStrategy;

    {
    	setClassFinderStrategy( new DefaultClassFinderStrategy() );
    }

    @Context
    protected HttpServletRequest _request;

    @Context
    protected HttpServletResponse _response;

    @Resource
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        this._classFinderStrategy = classFinderStrategy;
        _resultTraverser.setClassFinderStrategy( classFinderStrategy );
    }

    @Resource
    public void setSelectorParser( SelectorParser selectorParser )
    {
        this._selectorParser = selectorParser;
    }

    @Resource
    public void setRenderingListenerRegistry( RenderingListenerRegistry renderingListenerRegistry ) 
    {
        this._renderingListenerRegistry = renderingListenerRegistry;
    }

    @Resource
    public void setSelector( CoreSelector selector ) 
    {
        this._selector = selector;
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