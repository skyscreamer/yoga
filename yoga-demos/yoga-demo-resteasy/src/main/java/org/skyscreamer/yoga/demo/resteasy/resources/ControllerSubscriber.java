package org.skyscreamer.yoga.demo.resteasy.resources;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.core.ResourceMethodRegistry;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.util.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ControllerSubscriber<T>
{
    @Path("/{id}")
    public class DefaultController
    {
        @GET
        public T get( @PathParam("id") long id )
        {
            return genericDao.find( entityClass, id );
        }
    }

    Class<T> entityClass;

    ResourceMethodRegistry registry;
    @Autowired GenericDao genericDao;

    @Autowired
    public void setRegistry( ResourceMethodRegistry registry )
    {
        this.registry = registry;
    }

    public void setEntityClass( Class<T> entityClass )
    {
        this.entityClass = entityClass;
    }

    @PostConstruct
    /** register a new generic Controller that finds objects by id to /<user friendly class name>/{id} */
    public void setup()
    {
        registry.addSingletonResource( new DefaultController(), "/" + NameUtil.getName( entityClass ) );
    }
}
