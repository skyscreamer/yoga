package org.skyscreamer.yoga.demo.controller;

import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.Artist;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:02 PM
 */
@Controller
@Path("/artist")
public class ArtistController extends AbstractController<Artist>
{
}
