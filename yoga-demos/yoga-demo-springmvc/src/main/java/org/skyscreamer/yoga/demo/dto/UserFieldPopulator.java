package org.skyscreamer.yoga.demo.dto;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.populator.AbstractFieldPopulator;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
@Component( "userFieldPopulator" )
public class UserFieldPopulator extends AbstractFieldPopulator<User>
{
    @Autowired GenericDao _genericDao;
    @Autowired @Qualifier("albumFieldPopulator") FieldPopulator<Album> _albumFieldPopulator;
    @Autowired @Qualifier("artistFieldPopulator") FieldPopulator<Artist> _artistFieldPopulator;

    protected Collection<String> getCoreFieldNames()
    {
        return Arrays.asList( "id", "name" );
    }

    protected Collection<String> getModelFieldNames()
    {
        return Arrays.asList( "id", "name", "isFriend" );
    }

    protected String getUriTemplate()
    {
        return "/user/{id}";
    }

    protected Object constructFieldValue( String fieldName, User user, Selector selector )
    {
        if ( fieldName.equals( "friends" ) )
        {
            return this.populateListFields( user.getFriends(), selector.getField( fieldName ) );
        }
        else if ( fieldName.equals( "favoriteArtists" ) )
        {
            return _artistFieldPopulator.populateListFields( user.getFavoriteArtists(),
                    selector.getField( fieldName ) );
        }
        else if ( fieldName.equals( "recommendedAlbums" ) )
        {
            return _albumFieldPopulator.populateListFields( _genericDao.findAll( Album.class ),
                    selector.getField( fieldName ) );
        }

        return null;
    }
}
