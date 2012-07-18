package org.skyscreamer.yoga.view;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public abstract class AbstractXmlYogaView extends AbstractYogaView
{
    public void write( Element rootElement, OutputStream outputStream ) throws IOException
    {
        DOMDocument domDocument = new DOMDocument();
        domDocument.setRootElement( rootElement );
        domDocument.write( new OutputStreamWriter( outputStream ) );
        outputStream.flush();
    }
}
