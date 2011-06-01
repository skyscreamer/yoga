package org.skyscreamer.yoga.demo.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 5:07 PM
 */
@Controller
@Path("/user")
public class UserController{

	@Autowired GenericDao genericDao;
	
	@GET
	@Path("/{id}")
    public User get( @PathParam("id") long id )
    {
        return genericDao.find(User.class, id);
    }

	@GET
	public List<User> getUsers() 
	{
		return genericDao.findAll(User.class);
	}
}
