package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.User;
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
@RequestMapping("/artist")
public class ArtistController extends AbstractController<Artist>
{
    @Autowired @Qualifier("artistFieldPopulator") private FieldPopulator<Artist> _artistFieldPopulator;

    protected FieldPopulator<Artist> getAbstractFieldPopulator()
    {
        return _artistFieldPopulator;
    }
}
