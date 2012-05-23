package org.skyscreamer.yoga.view;

import java.io.IOException;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.XmlHierarchyModelImpl;
import org.skyscreamer.yoga.selector.Selector;

public class XmlSelectorView extends AbstractXmlYogaView
{
    @Override
    public void render( Selector selector, Object value, YogaRequestContext context )
            throws IOException
    {
        HierarchicalModel<Element> model = getModel( value );
        resultTraverser.traverse( value, selector, model, context );
        write( context, model.getUnderlyingModel() );
    }

    protected HierarchicalModel<Element> getModel( Object value )
    {
        if (value instanceof Iterable)
        {
            String name = getClassName( ((Iterable<?>) value).iterator().next() );
            DOMElement root = new DOMElement( "result" );
            return new XmlHierarchyModelImpl( root, name );
        }
        else
        {
            DOMElement root = new DOMElement( getClassName( value ) );
            return new XmlHierarchyModelImpl( root );
        }
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
