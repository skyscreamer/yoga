package org.skyscreamer.yoga.demo.test.controller;

import static org.skyscreamer.yoga.demo.util.TypeUtils.returnedClass;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
public abstract class AbstractController<T>
{
    @Autowired
    GenericDao _genericDao;

    Class<T> _entityClass = returnedClass( getClass() );

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

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
    public void notFound()
    {
    }
}
