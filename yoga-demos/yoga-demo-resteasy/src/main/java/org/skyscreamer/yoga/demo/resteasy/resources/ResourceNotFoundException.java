package org.skyscreamer.yoga.demo.resteasy.resources;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class ResourceNotFoundException implements ExceptionMapper<ObjectNotFoundException>
{
    @Override
    public Response toResponse( ObjectNotFoundException exception )
    {
        return Response.status( Response.Status.NOT_FOUND ).entity( "Resource not found" ).type( MediaType.TEXT_PLAIN ).build();
    }
}
