package org.skyscreamer.yoga.demo.test.jersey.standalone.resources;

import static org.skyscreamer.yoga.demo.util.TypeUtils.returnedClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.skyscreamer.yoga.demo.test.jersey.standalone.DemoData;

public class AbstractResource<T>
{

    DemoData demoData;
    Class<T> _entityClass = returnedClass( getClass() );

    public AbstractResource( DemoData demoData )
    {
        this.demoData = demoData;
    }
    
    @GET
    @Path("/{id}")
    public T get( @PathParam("id") long id, @QueryParam("selector") String selectorString )
    {
        return demoData.get( _entityClass, id );
    }

}
