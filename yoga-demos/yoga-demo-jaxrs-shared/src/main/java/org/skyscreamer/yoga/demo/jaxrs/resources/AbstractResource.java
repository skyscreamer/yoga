package org.skyscreamer.yoga.demo.jaxrs.resources;

import static org.skyscreamer.yoga.demo.util.TypeUtils.returnedClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;

public class AbstractResource<T>
{

    final GenericDao _dao;
    final Class<T> _entityClass = returnedClass( getClass() );

    public AbstractResource( GenericDao dao )
    {
        this._dao = dao;
    }

    @GET
    @Path("/{id:[0-9]+}")
    public T get( @PathParam("id") long id )
    {
        return _dao.find( _entityClass, id );
    }

}
