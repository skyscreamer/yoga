package org.skyscreamer.yoga.util;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class XmlYogaViewUtil
{
    public static void write( Element rootElement, OutputStream outputStream ) throws IOException
    {
        DOMDocument domDocument = new DOMDocument();
        domDocument.setRootElement( rootElement );
        OutputStreamWriter out = new OutputStreamWriter( outputStream );
        domDocument.write( out );
        out.flush();
        out.close();
    }
}
