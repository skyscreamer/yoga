package org.skyscreamer.yoga.demo.test.controller;

import org.skyscreamer.yoga.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
    @Controller
    @RequestMapping("/user")
    public class UserController extends AbstractController<User>
    {
        @RequestMapping
        public List<User> getUsers()
        {
            return _genericDao.findAll( User.class );
        }
    }
