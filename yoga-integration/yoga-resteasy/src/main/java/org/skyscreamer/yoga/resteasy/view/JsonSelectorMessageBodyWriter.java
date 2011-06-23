package org.skyscreamer.yoga.resteasy.view;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.selector.Selector;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
   @Context HttpServletRequest request;
   
   @Override
   public void writeTo(Object obj, Class<?> type, Type arg2, Annotation[] annotations,
         MediaType mediaType, MultivaluedMap<String, Object> headers, OutputStream output)
         throws IOException, WebApplicationException
   {
      new ObjectMapper().writeValue(output, getResult(obj, getSelector(request)));
   }

   private Object getResult(Object obj, Selector selector)
   {
      if (obj instanceof Iterable<?>)
      {
         return _fieldPopulator.populate((Iterable<?>) obj, selector);
      }
      else
      {
         return _fieldPopulator.populate(obj, selector);
      }
   }
}
