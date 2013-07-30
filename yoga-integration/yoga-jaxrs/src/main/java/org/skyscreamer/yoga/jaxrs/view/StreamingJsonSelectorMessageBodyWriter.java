package org.skyscreamer.yoga.jaxrs.view;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.view.AbstractYogaView;
import org.skyscreamer.yoga.view.StreamingJsonSelectorView;

@Singleton
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class StreamingJsonSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
    @Override
    protected AbstractYogaView createView()
    {
        return new StreamingJsonSelectorView();
    }
}
