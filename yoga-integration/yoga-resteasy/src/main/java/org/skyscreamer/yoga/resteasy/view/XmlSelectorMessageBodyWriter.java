package org.skyscreamer.yoga.resteasy.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.resteasy.mapper.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
   @Override
   public void writeTo(Object obj, Class<?> type, Type arg2, Annotation[] annotations,
         MediaType mediaType, MultivaluedMap<String, Object> headers, OutputStream output)
         throws IOException, WebApplicationException
   {
      HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
      Selector selector = getSelector(request);
      DOMDocument domDocument = new DOMDocument();
      DOMElement root = new DOMElement("result");
      domDocument.setRootElement(root);
      HierarchicalModel model = new XmlHierarchyModel(root);
      traverse(obj, selector, model);
      write(output, domDocument);
   }

   protected void traverse(Object obj, Selector selector, HierarchicalModel model) 
   {
	  ResultTraverser traverser = getTraverser();
      if (obj instanceof Iterable)
      {
         for (Object child : (Iterable<?>) obj)
         {
        	 traverser.traverse(child, selector, model);
         }
      }
      else
      {
    	  traverser.traverse(obj, selector, model);
      }
   }

   protected void write(OutputStream output, DOMDocument domDocument) throws IOException 
   {
	  OutputStreamWriter out = new OutputStreamWriter(output);
      domDocument.write(out);
      out.flush();
   }

}
