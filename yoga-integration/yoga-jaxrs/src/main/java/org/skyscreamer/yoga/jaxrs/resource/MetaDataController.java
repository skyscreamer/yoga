package org.skyscreamer.yoga.jaxrs.resource;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.metadata.TypeMetaData;

@Singleton
@Path(MetaDataController.ROOT)
public class MetaDataController
{
    public static final String ROOT = "/metadata/";

    @Inject
    MetaDataRegistry _metaDataRegistry;

    @Context
    HttpServletRequest request;

    public MetaDataController()
    {
    }

    public MetaDataController( MetaDataRegistry _metaDataRegistry )
    {
        this._metaDataRegistry = _metaDataRegistry;
    }

    public void setMetaDataRegistry( MetaDataRegistry metaDataRegistry )
    {
        this._metaDataRegistry = metaDataRegistry;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("{type}")
    public TypeMetaData getTypeMetaData( @PathParam("type") String type )
    {
        String uri = request.getRequestURI();
        String parts[] = uri.split( "\\." );
        return _metaDataRegistry.getMetaData( type, parts[parts.length - 1] );
    }

}
