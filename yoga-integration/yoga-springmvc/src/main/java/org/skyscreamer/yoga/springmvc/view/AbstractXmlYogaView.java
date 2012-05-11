package org.skyscreamer.yoga.springmvc.view;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletOutputStream;

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
        ServletOutputStream outputStream = context.getResponse().getOutputStream();
        domDocument.write( new OutputStreamWriter( outputStream ) );
        outputStream.flush();
    }
    

    protected String getClassName(Object obj)
    {
        Class<?> type = _classFinderStrategy.findClass( obj );
        return NameUtil.getName( type );
    }
}
