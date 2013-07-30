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
public class UserConfiguration extends YogaEntityConfiguration<User>
{
    @Autowired
    GenericDao _genericDao;

    public UserConfiguration()
    {
    }

    public UserConfiguration( GenericDao _genericDao )
    {
        this._genericDao = _genericDao;
    }

    @ExtraField("recommendedAlbums")
    public List<Album> getRecommendedAlbums(User user)
    {
        return _genericDao.findAll( Album.class );
    }
}
