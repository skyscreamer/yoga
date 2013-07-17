package org.skyscreamer.yoga.demo.test.jersey.resources;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.skyscreamer.yoga.demo.model.User;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */

@Singleton
@Path("/user")
public class UserController extends AbstractController<User>
{
    @GET
    public List<User> getUsers( @QueryParam("selector") String selectorString )
    {
        return _genericDao.findAll( User.class );
    }
}
