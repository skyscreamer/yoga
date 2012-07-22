package org.skyscreamer.yoga.demo.resteasy.resources;

import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.metadata.TypeMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Controller
@Path("/metadata/")
public class MetaDataController
{
    @Autowired
    MetaDataRegistry _metaDataRegistry;

    @Context
    HttpServletRequest request;

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
