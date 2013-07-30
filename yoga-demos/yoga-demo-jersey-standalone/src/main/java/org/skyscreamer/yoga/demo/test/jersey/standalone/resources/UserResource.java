package org.skyscreamer.yoga.demo.test.jersey.standalone.resources;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.User;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Path("/user")
public class UserResource extends AbstractResource<User>
{

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
