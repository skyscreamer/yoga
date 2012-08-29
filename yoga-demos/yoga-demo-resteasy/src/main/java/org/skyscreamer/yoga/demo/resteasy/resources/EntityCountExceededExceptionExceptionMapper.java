package org.skyscreamer.yoga.demo.resteasy.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.exceptions.EntityCountExceededException;

@Provider
public class EntityCountExceededExceptionExceptionMapper implements
        ExceptionMapper<EntityCountExceededException>
{

    @Override
    public Response toResponse( EntityCountExceededException exception )
    {
        return Response.status( Status.INTERNAL_SERVER_ERROR ).entity(exception.getMessage()).type( MediaType.TEXT_PLAIN_TYPE ).build();
    }

}
