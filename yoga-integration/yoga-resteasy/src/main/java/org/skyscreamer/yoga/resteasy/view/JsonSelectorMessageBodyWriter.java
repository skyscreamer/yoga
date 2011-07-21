package org.skyscreamer.yoga.resteasy.view;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.selector.Selector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

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
      new ObjectMapper().writeValue(output, getResult(obj, getSelector()));
   }

   private Object getResult(Object obj, Selector selector)
   {
      if (obj instanceof Iterable<?>)
      {
         return _resultMapper.mapOutput( (Iterable<?>) obj, selector );
      }
      else
      {
         return _resultMapper.mapOutput( obj, selector );
      }
   }
}
