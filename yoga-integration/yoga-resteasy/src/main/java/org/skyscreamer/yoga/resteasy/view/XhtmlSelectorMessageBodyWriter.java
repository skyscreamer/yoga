package org.skyscreamer.yoga.resteasy.view;

import org.skyscreamer.yoga.springmvc.view.AbstractYogaView;
import org.skyscreamer.yoga.springmvc.view.XhtmlSelectorView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.TEXT_HTML)
public class XhtmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
    @Override
    protected AbstractYogaView getView()
    {
        XhtmlSelectorView view = new XhtmlSelectorView();
        view.setResultTraverser( resultTraverser );
        return view;
    }
}
