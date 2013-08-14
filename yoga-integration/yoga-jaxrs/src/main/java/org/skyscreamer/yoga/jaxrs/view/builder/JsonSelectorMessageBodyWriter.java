package org.skyscreamer.yoga.jaxrs.view.builder;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.view.JsonSelectorView;

@Singleton
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonSelectorMessageBodyWriter extends AbstractYogaBuilderMessageBodyWriter
{
    public JsonSelectorMessageBodyWriter()
    {
        super( new JsonSelectorView() );
    }

    public JsonSelectorMessageBodyWriter( YogaBuilderViewFactory util )
    {
        super( util.createJsonSelectorView() );
    }
}
