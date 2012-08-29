package org.skyscreamer.yoga.demo.resteasy.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.skyscreamer.yoga.demo.model.User;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Path("/user")
public class UserController extends AbstractController<User>
{
    @GET
    public List<User> getUsers( @QueryParam("selector") String selectorString )
    {
        return _genericDao.findAll( User.class );
    }
}
