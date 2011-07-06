package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public abstract class AbstractController<T>
{
	@Autowired GenericDao _genericDao;

	Class<T> _entityClass = returnedClass();

	@GET
    @Path("/{id}")
    public Map<String,Object> get( @PathParam("id") long id, @QueryParam( "selector" ) String selectorString )
    {
        T obj = _genericDao.find( _entityClass, id);
        Selector selector = SelectorParser.parseSelector( selectorString );
        Map<String,Object> dto = getAbstractFieldPopulator().populateObjectFields( obj, selector );
		return dto;
    }

    // http://blog.xebia.com/2009/02/acessing-generic-types-at-runtime-in-java/
    @SuppressWarnings("unchecked")
	private Class<T> returnedClass()
    {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    protected abstract FieldPopulator<T> getAbstractFieldPopulator();
}