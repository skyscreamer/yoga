package org.skyscreamer.yoga.demo.jersey.resources;

import static org.skyscreamer.yoga.demo.util.TypeUtils.returnedClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;

import com.sun.jersey.api.core.InjectParam;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public abstract class AbstractController<T>
{
    GenericDao _genericDao;

    Class<T> _entityClass = returnedClass( getClass() );

    @InjectParam
    public void setGenericDao( GenericDao _genericDao )
    {
        this._genericDao = _genericDao;
    }

    @GET
    @Path("/{id}")
    public T get( @PathParam("id") long id, @QueryParam("selector") String selectorString )
    {
        return _genericDao.find( _entityClass, id );
    }
}
