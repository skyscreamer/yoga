package org.skyscreamer.yoga.demo.populator;

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
@Component( "artistFieldPopulator" )
public class ArtistFieldPopulator extends AbstractFieldPopulator<Artist>
{
    @Autowired @Qualifier("albumFieldPopulator") FieldPopulator<Album> _albumFieldPopulator;
    @Autowired @Qualifier("userFieldPopulator") FieldPopulator<User> _userFieldPopulator;

    protected Collection<String> getCoreFieldNames()
    {
        return Arrays.asList( "id", "name" );
    }

    protected Collection<String> getModelFieldNames()
    {
        return Arrays.asList( "id", "name" );
    }

    protected Object constructFieldValue( String fieldName, Artist artist, Selector selector )
    {
        if ( fieldName.equals( "fans" ) )
        {
            return _userFieldPopulator.populateListFields( artist.getFans(), selector.getField( fieldName ) );
        }
        else if ( fieldName.equals( "albums" ) )
        {
            return _albumFieldPopulator.populateListFields( artist.getAlbums(), selector.getField( fieldName ) );
        }
        return null;
    }
}
