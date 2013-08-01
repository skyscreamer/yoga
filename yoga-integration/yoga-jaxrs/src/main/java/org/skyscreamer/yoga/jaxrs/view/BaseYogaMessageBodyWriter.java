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

import org.skyscreamer.yoga.view.AbstractYogaView;

public class BaseYogaMessageBodyWriter implements MessageBodyWriter<Object>
{
    @Context
    protected HttpServletRequest _request;

    @Context
    protected HttpServletResponse _response;

    protected AbstractYogaView _view;

    public BaseYogaMessageBodyWriter( AbstractYogaView view )
    {
        this._view = view;
    }

    @Override
    public long getSize( Object value, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType )
    {
        return -1;
    }

    @Override
    public boolean isWriteable( Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType )
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
            _view.render( _request, _response, t, entityStream );
        }
        catch (Exception e)
        {
            throw new WebApplicationException( e, Response.Status.BAD_REQUEST );
        }
    }
}