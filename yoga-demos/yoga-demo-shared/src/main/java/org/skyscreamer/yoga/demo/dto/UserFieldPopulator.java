package org.skyscreamer.yoga.demo.dto;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.ExtraField;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.FieldPopulatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
@Service
public class UserFieldPopulator implements FieldPopulator<User>
{
    @Autowired GenericDao _genericDao;

    public List<String> getCoreFields()
    {
        return Arrays.asList( "id", "name" );
    }

    public List<String> getSupportedFields()
    {
        return Arrays.asList( "id", "name", "favoriteArtists" );
    }

    public String getUriTemplate()
    {
        return "/user/{id}";
    }

    @ExtraField( "recommendedAlbums" )
    public List<Album> getRecommendedAlbums()
    {
        return _genericDao.findAll( Album.class );
    }
}
