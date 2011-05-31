package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.controller.ControllerResponse;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController<User>
{
	@RequestMapping
	public @ResponseBody ControllerResponse getUsers( Selector selector )
    {
		return new ControllerResponse( selector, genericDao.findAll(User.class) );
	}
}
