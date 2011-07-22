package org.skyscreamer.yoga.resteasy.view;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.XhtmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

@Provider
@Produces(MediaType.TEXT_HTML)
public class XhtmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
   @Override
   public void writeTo(Object value, Class<?> type, Type arg2, Annotation[] annotations,
         MediaType mediaType, MultivaluedMap<String, Object> headers, OutputStream output)
         throws IOException, WebApplicationException
   {
      Selector selector = getSelector();
      DOMDocument domDocument = new DOMDocument();
      domDocument.setRootElement(new DOMElement("html"));
      Element body = domDocument.getRootElement().addElement("body");
      if (value instanceof Iterable)
      {
         for (Object child : (Iterable<?>) value)
         {
            traverse(child, selector, resultTraverser, body);
         }
      }
      else
      {
         traverse(value, selector, resultTraverser, body);
      }
      write(output, domDocument);
   }

   public void traverse(Object value, Selector selector, ResultTraverser traverser, Element body)
   {
      String name = NameUtil.getName(traverser.getClass(value));
      HierarchicalModel model = new XhtmlHierarchyModel(body.addElement("div").addAttribute("class", name));
      traverser.traverse(value, selector, model);
   }
   
}
