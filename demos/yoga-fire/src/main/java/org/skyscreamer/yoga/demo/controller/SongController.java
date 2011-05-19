package org.skyscreamer.yoga.demo.controller;

import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.Song;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:06 PM
 */
@Controller
@Path("/song")
public class SongController extends AbstractController<Song>
{
}
