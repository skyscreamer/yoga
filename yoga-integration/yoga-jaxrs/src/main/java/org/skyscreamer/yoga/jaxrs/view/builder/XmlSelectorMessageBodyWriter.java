package org.skyscreamer.yoga.jaxrs.view.builder;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.view.XmlSelectorView;

@Singleton
@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlSelectorMessageBodyWriter extends AbstractYogaBuilderMessageBodyWriter
{
    public XmlSelectorMessageBodyWriter()
    {
        super( new XmlSelectorView() );
    }
    
    public XmlSelectorMessageBodyWriter( YogaBuilderViewFactory util )
    {
        super( util.createXmlSelectorView() );
    }
}
