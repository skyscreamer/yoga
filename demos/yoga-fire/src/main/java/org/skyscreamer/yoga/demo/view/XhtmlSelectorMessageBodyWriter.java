package org.skyscreamer.yoga.demo.view;

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

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.skyscreamer.yoga.demo.traverser.XhtmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.traverser.HierarchicalModel;
import org.skyscreamer.yoga.traverser.ObjectFieldTraverser;
import org.springframework.stereotype.Service;

@Service
@Provider
@Produces(MediaType.TEXT_HTML)
public class XhtmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
   @Override
   public void writeTo(Object obj, Class<?> type, Type arg2, Annotation[] annotations,
         MediaType mediaType, MultivaluedMap<String, Object> headers, OutputStream output)
         throws IOException, WebApplicationException
   {
      HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
      Selector selector = getSelector(request);
      DOMDocument domDocument = new DOMDocument();
      domDocument.setRootElement( new DOMElement("html"));
      Element body = domDocument.getRootElement().addElement("body");
      if (obj instanceof Iterable)
      {
         for (Object child : (Iterable<?>) obj)
         {
            traverse(selector, body, child);
         }
      }
      else
      {
         traverse(selector, body, obj);
      }
      OutputStreamWriter out = new OutputStreamWriter(output);
      domDocument.write(out);
      out.flush();
   }

   protected void traverse(Selector selector, Element body, Object child)
   {
      ObjectFieldTraverser traverser = getTraverser();
	  String name = NameUtil.getName(traverser.getClass(child));
      HierarchicalModel model = new XhtmlHierarchyModel(body.addElement("div").addAttribute("class", name));
      traverser.traverse(child, selector, model);
   }
}
