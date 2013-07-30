package org.skyscreamer.yoga.demo.test.jersey.standalone.resources;

import static org.skyscreamer.yoga.demo.util.TypeUtils.returnedClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;

public class AbstractResource<T>
{

    GenericDao _dao;
    Class<T> _entityClass = returnedClass( getClass() );

    public AbstractResource( GenericDao dao )
    {
        this._dao = dao;
    }
    
    @GET
    @Path("/{id}")
    public T get( @PathParam("id") long id, @QueryParam("selector") String selectorString )
    {
        return _dao.find( _entityClass, id );
    }

}
