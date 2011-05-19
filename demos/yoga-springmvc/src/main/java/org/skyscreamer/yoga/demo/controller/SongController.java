package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.demo.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:06 PM
 */
@Controller
@RequestMapping("/song")
public class SongController extends AbstractController<Song>
{
}
