package org.skyscreamer.yoga.jaxrs.view.builder;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.view.StreamingJsonSelectorView;

@Singleton
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class StreamingJsonSelectorMessageBodyWriter extends AbstractYogaBuilderMessageBodyWriter
{
    public StreamingJsonSelectorMessageBodyWriter()
    {
        super( new StreamingJsonSelectorView() );
    }
    
    public StreamingJsonSelectorMessageBodyWriter( YogaBuilderViewFactory util )
    {
        super( util.createStreamingJsonSelectorView() );
    }
}
