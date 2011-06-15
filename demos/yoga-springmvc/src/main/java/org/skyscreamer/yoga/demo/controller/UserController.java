package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.controller.ControllerResponse;
import org.skyscreamer.yoga.demo.dto.UserDto;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController<User>
{
    // private FieldPopulator<User,UserDto> _userFieldPopulator;

	@RequestMapping
	public @ResponseBody ControllerResponse getUsers( Selector selector )
    {
        return new ControllerResponse( selector, genericDao.findAll(User.class) );
//
//        List<User> users = genericDao.findAll( User.class );
//        List<UserDto> userDtos = _userFieldPopulator.populateListFields( users, selector );
//        return new ControllerResponse( selector, userDtos );
	}
}
