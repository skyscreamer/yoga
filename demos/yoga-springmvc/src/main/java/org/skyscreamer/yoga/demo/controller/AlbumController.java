package org.skyscreamer.yoga.demo.controller;

import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:06 PM
 */
@Controller
@RequestMapping("/album")
public class AlbumController extends AbstractController<Album>
{
    @Autowired @Qualifier("albumFieldPopulator") private FieldPopulator<Album> _albumFieldPopulator;

    protected FieldPopulator<Album> getAbstractFieldPopulator()
    {
        return _albumFieldPopulator;
    }
}
