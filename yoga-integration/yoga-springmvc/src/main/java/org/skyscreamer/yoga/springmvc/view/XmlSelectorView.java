package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

public class XmlSelectorView extends AbstractYogaView
{
   @Override
   public void render(OutputStream outputStream, Selector selector, Object value)
         throws IOException
   {
      DOMDocument domDocument = new DOMDocument();
      if (value instanceof Iterable)
      {
         DOMElement root = createDocument( domDocument, "result" );
         HierarchicalModel model = new XmlHierarchyModel( root );
         for (Object child : (Iterable<?>) value)
         {
            resultTraverser.traverse( child, selector, model, getHrefSuffix() );
         }
      }
      else
      {
         String name = NameUtil.getName( resultTraverser.getClass( value ) );
         DOMElement root = createDocument( domDocument, name );
         resultTraverser.traverse( value, selector, new XmlHierarchyModel( root ), getHrefSuffix() );
      }
      write( outputStream, domDocument );
   }

   public DOMElement createDocument(DOMDocument domDocument, String name)
   {
      DOMElement root = new DOMElement( name );
      domDocument.setRootElement( root );
      return root;
   }

   @Override
   public String getContentType()
   {
      return "application/xml";
   }
   
   @Override
   public String getHrefSuffix()
   {
      return "xml";
   }
}
