package org.skyscreamer.yoga.demo.controller;

import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.Album;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:06 PM
 */
@Controller
@Path("/album")
public class AlbumController extends AbstractController<Album>
{
}
