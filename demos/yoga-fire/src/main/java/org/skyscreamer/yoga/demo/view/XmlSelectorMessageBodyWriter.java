package org.skyscreamer.yoga.demo.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.skyscreamer.yoga.demo.traverser.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.traverser.HierarchicalModel;
import org.skyscreamer.yoga.traverser.ObjectFieldTraverser;
import org.springframework.stereotype.Service;

@Service
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
      ObjectFieldTraverser traverser = getTraverser();
      if (obj instanceof Collection)
      {
         for (Object child : (Collection<?>) obj)
         {
        	 traverser.traverse(child, selector, model);
         }
      }
      else
      {
    	  traverser.traverse(obj, selector, model);
      }
      OutputStreamWriter out = new OutputStreamWriter(output);
      domDocument.write(out);
      out.flush();
   }

}
