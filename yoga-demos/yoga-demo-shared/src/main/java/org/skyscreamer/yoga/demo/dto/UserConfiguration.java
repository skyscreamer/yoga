package org.skyscreamer.yoga.demo.dto;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserConfiguration extends YogaEntityConfiguration
{
    @Autowired
    GenericDao _genericDao;

    @Override
    public Class getEntityClass() {
        return User.class;
    }

    @ExtraField("recommendedAlbums")
    public List<Album> getRecommendedAlbums(User user)
    {
        return _genericDao.findAll( Album.class );
    }
}
