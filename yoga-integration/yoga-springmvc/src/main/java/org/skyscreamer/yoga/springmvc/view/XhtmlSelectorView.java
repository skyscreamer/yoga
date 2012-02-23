package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.XhtmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

public class XhtmlSelectorView extends AbstractYogaView
{

   @Override
   public void render(OutputStream outputStream, Selector selector, Object value, HttpServletResponse response)
         throws IOException
   {
      DOMDocument domDocument = new DOMDocument();
      Element rootElement = new DOMElement( "html" );
      domDocument.setRootElement( rootElement );
      Element cssLink = rootElement.addElement( "head" ).addElement( "link" );
      cssLink.addAttribute( "href", "/css/xhtml.css" );
      cssLink.addAttribute( "rel", "stylesheet" );
      Element body = rootElement.addElement( "body" );

      if (value instanceof Iterable)
      {
         for (Object child : (Iterable<?>) value)
         {
            traverse( response, child, selector, resultTraverser, body );
         }
      }
      else
      {
         traverse( response, value, selector, resultTraverser, body );
      }
      write( outputStream, domDocument );
   }

   public void traverse(HttpServletResponse response, Object value, Selector selector, ResultTraverser traverser, Element body)
   {
      String name = NameUtil.getName( traverser.findClass( value ) );
      HierarchicalModel model = new XhtmlHierarchyModel( body.addElement( "div" ).addAttribute(
            "class", name ) );
      traverser.traverse( value, selector, model, getHrefSuffix(), response );
   }

   @Override
   public String getContentType()
   {
      return "text/html";
   }

   @Override
   public String getHrefSuffix()
   {
      return "xhtml";
   }
}
