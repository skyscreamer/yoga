package org.skyscreamer.yoga.resteasy.view;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.springmvc.view.AbstractYogaView;
import org.skyscreamer.yoga.springmvc.view.XhtmlSelectorView;

@Provider
@Produces(MediaType.TEXT_HTML)
public class XhtmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
    @Override
    protected AbstractYogaView getView()
    {
        return new XhtmlSelectorView();
    }
}
