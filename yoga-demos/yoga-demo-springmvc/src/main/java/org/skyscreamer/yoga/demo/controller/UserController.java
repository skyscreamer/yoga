package org.skyscreamer.yoga.demo.controller;

import java.util.List;

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
	public @ResponseBody List<User> getUsers( Selector selector )
    {
        return _genericDao.findAll( User.class );
	}
}
