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
import org.skyscreamer.yoga.demo.util.NameUtil;
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
      traverse(obj, selector, body);
      write(output, domDocument);
   }

   protected void traverse(Object obj, Selector selector, Element body) {
      ObjectFieldTraverser traverser = getTraverser();
	  if (obj instanceof Iterable)
      {
         for (Object child : (Iterable<?>) obj)
         {
			  HierarchicalModel model = createModel(child, body, traverser);
			  traverser.traverse(child, selector, model);
         }
      }
      else
      {
		  HierarchicalModel model = createModel(obj, body, traverser);
		  traverser.traverse(obj, selector, model);
      }
   }

    protected HierarchicalModel createModel(Object obj, Element body, ObjectFieldTraverser traverser) {
	   String name = NameUtil.getName(traverser.getClass(obj));
	   return new XhtmlHierarchyModel(body.addElement("div").addAttribute("class", name));
   }
    
   protected void write(OutputStream output, DOMDocument domDocument) throws IOException 
   {
 	  OutputStreamWriter out = new OutputStreamWriter(output);
      domDocument.write(out);
      out.flush();
   }


}
