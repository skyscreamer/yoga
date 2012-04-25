package org.skyscreamer.yoga.resteasy.view;

import org.skyscreamer.yoga.springmvc.view.AbstractYogaView;
import org.skyscreamer.yoga.springmvc.view.JsonSelectorView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
    @Override
    protected AbstractYogaView getView()
    {
        JsonSelectorView view = new JsonSelectorView();
        view.setResultTraverser( resultTraverser );
        return view;
    }
}
