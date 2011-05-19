package org.skyscreamer.yoga.demo.controller;

import java.util.List;

import org.skyscreamer.yoga.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 5:07 PM
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController<User> {

	@RequestMapping
	public @ResponseBody List<User> getUsers() {
		return genericDao.findAll(User.class);
	}
}
