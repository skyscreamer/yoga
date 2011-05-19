package org.skyscreamer.yoga.demo.controller;

import java.lang.reflect.ParameterizedType;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping("/{id}")
    public @ResponseBody T get( @PathVariable long id )
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
