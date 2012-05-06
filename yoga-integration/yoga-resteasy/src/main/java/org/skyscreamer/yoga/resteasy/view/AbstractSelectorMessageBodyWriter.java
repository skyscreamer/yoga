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

import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.springmvc.view.AbstractYogaView;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSelectorMessageBodyWriter implements MessageBodyWriter<Object> {
	@Autowired
	protected ResultTraverser resultTraverser;

	@Autowired
	protected SelectorParser _selectorParser;

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	public AbstractSelectorMessageBodyWriter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getSize(Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3) {
		return true;
	}

<<<<<<< HEAD
	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {
		getView().render(entityStream, getSelector(), t, response);
	}
=======
   @Override
   public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations,
         MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
         throws IOException, WebApplicationException
   {
       try {
           getView().render( entityStream, getSelector(), t, response );
       } catch (ParseSelectorException e) {
           throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
       }
   }
>>>>>>> upstream/master

	protected abstract AbstractYogaView getView();

<<<<<<< HEAD
	protected Selector getSelector() {
		String selectorString = request.getParameter("selector");
		return _selectorParser.parseSelector(selectorString);
	}
=======
   protected Selector getSelector() throws ParseSelectorException {
      String selectorString = request.getParameter( "selector" );
      return _selectorParser.parseSelector( selectorString );
   }
>>>>>>> upstream/master

}
