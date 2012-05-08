package org.skyscreamer.yoga.springmvc.view;
import java.io.IOException;
import java.io.PrintWriter;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractXmlYogaView extends AbstractYogaView
{

    @Autowired
    protected ClassFinderStrategy _classFinderStrategy;

    public void write(YogaRequestContext context, Element rootElement) throws IOException
    {
        DOMDocument domDocument = new DOMDocument();
        domDocument.setRootElement( rootElement );
        PrintWriter writer = context.getResponse().getWriter();
        domDocument.write( writer );
        writer.flush();
    }
    

    protected String getClassName(Object obj)
    {
        Class<?> type = _classFinderStrategy.findClass( obj );
        return NameUtil.getName( type );
    }
}
