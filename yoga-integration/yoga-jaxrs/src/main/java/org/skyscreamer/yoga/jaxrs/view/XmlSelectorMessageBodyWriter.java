package org.skyscreamer.yoga.jaxrs.view;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.view.AbstractYogaView;
import org.skyscreamer.yoga.view.XmlSelectorView;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
    @Override
    protected AbstractYogaView getView()
    {
        return new XmlSelectorView();
    }
}
