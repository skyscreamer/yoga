package org.skyscreamer.yoga.demo.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.User;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 5:07 PM
 */
@Controller
@Path("/user")
public class UserController extends AbstractController<User> {

	@GET
	public List<User> getUsers() {
		return genericDao.findAll(User.class);
	}
}
