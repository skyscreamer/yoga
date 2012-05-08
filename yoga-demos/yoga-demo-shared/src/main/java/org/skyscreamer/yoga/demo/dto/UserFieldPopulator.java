package org.skyscreamer.yoga.demo.dto;

import java.util.List;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.annotations.PopulationExtension;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.FieldPopulatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA. User: corby
 */
@Service
@PopulationExtension(User.class)
public class UserFieldPopulator extends FieldPopulatorSupport
{
    @Autowired
    GenericDao _genericDao;

    @ExtraField("recommendedAlbums")
    public List<Album> getRecommendedAlbums()
    {
        return _genericDao.findAll( Album.class );
    }
}
