package org.skyscreamer.yoga.demo.jaxrs.resources;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.User;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Singleton
@Path("/user")
public class UserResource extends AbstractResource<User>
{

    @Inject
    public UserResource( GenericDao dao )
    {
        super( dao );
    }

    @GET
    public Collection<User> getAll()
    {
        return _dao.findAll( User.class );
    }
}
