package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.NameUtil;

public class XmlSelectorView extends AbstractYogaView
{
    @Override
    public void render(OutputStream outputStream, Selector selector, Object value,
            HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        YogaRequestContext context = new YogaRequestContext( getHrefSuffix(), request, response );

        DOMDocument domDocument = new DOMDocument();
        String rootName = getRootName( value );
        DOMElement root = new DOMElement( rootName );
        domDocument.setRootElement( root );
        HierarchicalModel model = new XmlHierarchyModel( root );
        resultTraverser.traverse( value, selector, model, context );
        write( outputStream, domDocument );
    }

    protected String getRootName(Object value)
    {
        if (value instanceof Iterable)
        {
            return "result";
        }
        else
        {
            return NameUtil.getName( _classFinderStrategy.findClass( value ) );
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
