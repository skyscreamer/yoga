package org.skyscreamer.yoga.demo.controller;

import java.lang.reflect.ParameterizedType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:06 PM
 */
public abstract class AbstractController<T>
{
	@Autowired GenericDao genericDao;
	
	Class<T> entityClass = returnedClass();

	@GET
	@Path("/{id}")
    public T get( @PathParam("id") long id )
    {
        T obj = genericDao.find(entityClass, id);
		return obj;
    }

    // http://blog.xebia.com/2009/02/acessing-generic-types-at-runtime-in-java/
    @SuppressWarnings("unchecked")
	private Class<T> returnedClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}
