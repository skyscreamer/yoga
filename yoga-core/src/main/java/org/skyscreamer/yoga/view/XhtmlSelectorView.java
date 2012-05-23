package org.skyscreamer.yoga.view;

import java.io.IOException;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.XhtmlHierarchyModelImpl;
import org.skyscreamer.yoga.selector.Selector;

public class XhtmlSelectorView extends AbstractXmlYogaView
{

    @Override
    public void render( Selector selector, Object value, YogaRequestContext context )
            throws IOException
    {
        Element rootElement = new DOMElement( "html" );
        initHead( rootElement );
        HierarchicalModel<Element> model = getModel( value, rootElement );
        resultTraverser.traverse( value, selector, model, context );
        write( context, rootElement );
    }

    protected HierarchicalModel<Element> getModel( Object value, Element rootElement )
    {
        Element topDiv = rootElement.addElement( "body" ).addElement( "div" )
                .addAttribute( "class", getClassName( value ) );

        return new XhtmlHierarchyModelImpl( topDiv );
    }

    protected void initHead( Element rootElement )
    {
        Element cssLink = rootElement.addElement( "head" ).addElement( "link" );
        cssLink.addAttribute( "href", "/css/xhtml.css" );
        cssLink.addAttribute( "rel", "stylesheet" );
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
