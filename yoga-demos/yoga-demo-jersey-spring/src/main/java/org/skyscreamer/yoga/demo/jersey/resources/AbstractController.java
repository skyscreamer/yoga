package org.skyscreamer.yoga.demo.jersey.resources;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public abstract class AbstractController<T>
{
    @Inject
    GenericDao _genericDao;

    Class<T> _entityClass = returnedClass();

    @GET
    @Path("/{id}")
    public T get( @PathParam("id") long id, @QueryParam("selector") String selectorString )
    {
        return _genericDao.find( _entityClass, id );
    }

    // http://blog.xebia.com/2009/02/acessing-generic-types-at-runtime-in-java/
    @SuppressWarnings("unchecked")
    private Class<T> returnedClass()
    {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}
