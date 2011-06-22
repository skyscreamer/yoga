package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.controller.ControllerResponse;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController<User>
{
    @Autowired @Qualifier("userFieldPopulator") private FieldPopulator<User> _userFieldPopulator;

	@RequestMapping
	public @ResponseBody ControllerResponse getUsers( Selector selector )
    {
        List<User> users = _genericDao.findAll( User.class );
        List<Map<String,Object>> userDtos = _userFieldPopulator.populateListFields( users, selector );

        return new ControllerResponse( userDtos );
	}

    protected FieldPopulator<User> getAbstractFieldPopulator()
    {
        return _userFieldPopulator;
    }
}
