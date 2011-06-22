package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
@Controller
@RequestMapping("/song")
public class SongController extends AbstractController<Song>
{
    @Autowired @Qualifier("songFieldPopulator") private FieldPopulator<Song> _songFieldPopulator;

    protected FieldPopulator<Song> getAbstractFieldPopulator()
    {
        return _songFieldPopulator;
    }
}
