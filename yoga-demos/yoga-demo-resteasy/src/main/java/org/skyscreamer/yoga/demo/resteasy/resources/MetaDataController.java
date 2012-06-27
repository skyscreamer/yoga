package org.skyscreamer.yoga.demo.resteasy.resources;

import org.skyscreamer.yoga.metadata.MetaDataService;
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
    MetaDataService metaDataService;

    @Context
    HttpServletRequest request;

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("{type}")
    public TypeMetaData getTypeMetaData( @PathParam("type") String type )
    {
        String uri = request.getRequestURI();
        String parts[] = uri.split( "\\." );
        return metaDataService.getMetaData( type, parts[parts.length - 1] );
    }

}
