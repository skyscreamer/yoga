package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.XhtmlHierarchyModelImpl;
import org.skyscreamer.yoga.util.XmlYogaViewUtil;

public class XhtmlSelectorView extends AbstractYogaView
{
    @Override
    public void render( Object value, YogaRequestContext context, OutputStream os )
            throws IOException
    {
        Element rootElement = new DOMElement( "html" );
        initHead( rootElement );
        HierarchicalModel<Element> model = getModel( value, rootElement );
        _resultTraverser.traverse( value, context.getSelector(), model, context );
        XmlYogaViewUtil.write( rootElement, os );
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
