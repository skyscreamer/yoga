package org.skyscreamer.yoga.demo.dto;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.annotations.PopulationExtension;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PopulationExtension(User.class)
public class UserFieldPopulator
{
    @Autowired
    GenericDao _genericDao;

    @ExtraField("recommendedAlbums")
    public List<Album> getRecommendedAlbums()
    {
        return _genericDao.findAll( Album.class );
    }
}
