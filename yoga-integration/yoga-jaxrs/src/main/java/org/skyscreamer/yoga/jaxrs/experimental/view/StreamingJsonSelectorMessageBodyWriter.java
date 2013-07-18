package org.skyscreamer.yoga.jaxrs.experimental.view;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.jaxrs.view.AbstractSelectorMessageBodyWriter;
import org.skyscreamer.yoga.view.AbstractYogaView;
import org.skyscreamer.yoga.view.StreamingJsonSelectorView;

@Singleton
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class StreamingJsonSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
	public StreamingJsonSelectorMessageBodyWriter() {
		// TODO Auto-generated constructor stub
	}
    @Override
    protected AbstractYogaView getView()
    {
        return new StreamingJsonSelectorView();
    }
}
