package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
@Controller
@Path("/user")
public class UserController extends AbstractController<User>
{
	@Autowired @Qualifier("userFieldPopulator") private FieldPopulator<User> _userFieldPopulator;

    protected FieldPopulator<User> getAbstractFieldPopulator()
    {
        return _userFieldPopulator;
    }

    @GET
    public List<Map<String,Object>> getUsers( @QueryParam( "selector" ) String selectorString )
    {
        List<User> users = _genericDao.findAll( User.class );
        Selector selector = SelectorParser.parseSelector( selectorString );
        List<Map<String,Object>> userDtos = _userFieldPopulator.populateListFields( users, selector );
        return userDtos;
	}
}
