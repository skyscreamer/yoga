package org.skyscreamer.yoga.jaxrs.view;

import org.skyscreamer.yoga.view.AbstractYogaView;
import org.skyscreamer.yoga.view.SelectorBuilderView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.TEXT_HTML)
public class SelectorBuilderMessageBodyWriter extends AbstractSelectorMessageBodyWriter {
    @Override
    protected AbstractYogaView getView()
    {
        return new SelectorBuilderView();
    }
}
