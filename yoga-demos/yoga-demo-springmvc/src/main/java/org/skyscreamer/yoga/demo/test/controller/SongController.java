package org.skyscreamer.yoga.demo.test.controller;

import org.skyscreamer.yoga.demo.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Controller
@RequestMapping("/song")
public class SongController extends AbstractController<Song>
{
}
