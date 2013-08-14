package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.XmlHierarchyModelImpl;
import org.skyscreamer.yoga.util.XmlYogaViewUtil;

/**
 * This class represents an xml yoga view. It will return an xml representation
 * of either single objects or a list of objects
 * 
 * @author solomon.duskis
 * 
 * @see AbstractYogaView
 */
public class XmlSelectorView extends AbstractYogaView
{
    @Override
    public void render( Object value, YogaRequestContext context, OutputStream os )
            throws IOException
    {
        HierarchicalModel<Element> model = null;
        if (value instanceof Iterable)
        {
            String name = getClassName( ( ( Iterable<?> ) value ).iterator().next() );
            model = new XmlHierarchyModelImpl( new DOMElement( "result" ), name );
        }
        else
        {
            model = new XmlHierarchyModelImpl( new DOMElement( getClassName( value ) ) );
        }

        _resultTraverser.traverse( value, context.getSelector(), model, context );
        XmlYogaViewUtil.write( model.getUnderlyingModel(), os );
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
