package org.skyscreamer.yoga.demo.resteasy.resources;

import java.lang.reflect.ParameterizedType;

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

    Class<T> _entityClass = returnedClass();

    @GET
    @Path("/{id:[0-9]+}")
    public T get( @PathParam("id") long id )
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

    @GET
    @Path("/count")
    public Number getCount()
    {
        return _genericDao.getCount(_entityClass);
    }

}
