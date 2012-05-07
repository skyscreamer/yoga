package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.XhtmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

public class XhtmlSelectorView extends AbstractYogaView
{

    @Override
    public void render(OutputStream outputStream, Selector selector, Object value,
            HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        YogaRequestContext context = new YogaRequestContext( getHrefSuffix(), request, response );

        DOMDocument domDocument = new DOMDocument();
        Element rootElement = new DOMElement( "html" );
        domDocument.setRootElement( rootElement );
        Element cssLink = rootElement.addElement( "head" ).addElement( "link" );
        cssLink.addAttribute( "href", "/css/xhtml.css" );
        cssLink.addAttribute( "rel", "stylesheet" );
        Element body = rootElement.addElement( "body" );

        String name = NameUtil.getName( _classFinderStrategy.findClass( value ) );
        HierarchicalModel model = new XhtmlHierarchyModel( body.addElement( "div" ).addAttribute(
                "class", name ) );
        resultTraverser.traverse( value, selector, model, context );

        write( outputStream, domDocument );
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
