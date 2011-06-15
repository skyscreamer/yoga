package org.skyscreamer.yoga.demo.dto;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.AbstractFieldPopulator;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public class UserFieldPopulator extends AbstractFieldPopulator<User, UserDto>
{
    @Autowired GenericDao _genericDao;

    protected Object constructFieldValue( PropertyDescriptor property, User user )
    {
        if ( property.getName().equals( "id" ) )
        {
            return user.getId();
        }
        else if ( property.getName().equals( "name" ) )
        {
            return user.getName();
        }
        else if ( property.getName().equals( "recommendedAlbums" ) )
        {
            return _genericDao.findAll( Album.class );
        }

        return null;
    }
}
