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

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
   @Override
   public void writeTo(Object value, Class<?> type, Type arg2, Annotation[] annotations,
         MediaType mediaType, MultivaluedMap<String, Object> headers, OutputStream output)
         throws IOException, WebApplicationException
   {
      Selector selector = getSelector();
      ResultTraverser traverser = getTraverser();
      DOMDocument domDocument = new DOMDocument();
      if (value instanceof Iterable)
      {
         DOMElement root = new DOMElement("result");
         domDocument.setRootElement(root);
         HierarchicalModel model = new XmlHierarchyModel(root);
         for (Object child : (Iterable<?>) value)
         {
            traverser.traverse(child, selector, model);
         }
      }
      else
      {
         DOMElement root = new DOMElement(NameUtil.getName(traverser.getClass(value)));
         domDocument.setRootElement(root);
         HierarchicalModel model = new XmlHierarchyModel(root);
         traverser.traverse(value, selector, model);
      }
      write(output, domDocument);
   }
}
