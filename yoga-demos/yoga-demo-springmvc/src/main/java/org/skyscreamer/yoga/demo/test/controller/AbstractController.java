package org.skyscreamer.yoga.demo.test.controller;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
public abstract class AbstractController<T>
{
    @Autowired
    GenericDao _genericDao;

    Class<T> _entityClass = returnedClass();

    @RequestMapping("/{id}")
//    @ResponseBody
    public T get( @PathVariable long id )
    {
        return _genericDao.find( _entityClass, id );
    }

    @RequestMapping
//    @ResponseBody
    public List<T> getAll()
    {
        return _genericDao.findAll(_entityClass);
    }

    @RequestMapping("/count")
    @ResponseBody
    public String getCount(ModelAndView mv)
    {
        return String.valueOf(_genericDao.getCount(_entityClass));
    }

    // http://blog.xebia.com/2009/02/acessing-generic-types-at-runtime-in-java/
    @SuppressWarnings("unchecked")
    private Class<T> returnedClass()
    {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
    public void notFound()
    {
    }
}
