package org.skyscreamer.yoga.demo.resteasy.resources;

import static org.skyscreamer.yoga.demo.util.TypeUtils.returnedClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public abstract class AbstractController<T>
{
    @Autowired
    GenericDao _genericDao;

    Class<T> _entityClass = returnedClass( getClass() );

    @GET
    @Path("/{id:[0-9]+}")
    public T get( @PathParam("id") long id )
    {
        return _genericDao.find( _entityClass, id );
    }

}
