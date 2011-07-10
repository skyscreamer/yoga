package org.skyscreamer.yoga.demo.populator;

import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
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
@Component( "albumFieldPopulator" )
public class AlbumFieldPopulator extends AbstractFieldPopulator<Album>
{
    @Autowired @Qualifier("artistFieldPopulator") FieldPopulator<Artist> _artistFieldPopulator;
    @Autowired @Qualifier("songFieldPopulator") FieldPopulator<Song> _songFieldPopulator;

    protected Collection<String> getCoreFieldNames()
    {
        return Arrays.asList( "id", "title", "year" );
    }

    protected Collection<String> getModelFieldNames()
    {
        return Arrays.asList( "id", "title", "year" );
    }

    protected Object constructFieldValue( String fieldName, Album album, Selector selector )
    {
        if ( fieldName.equals( "artist" ) )
        {
            return _artistFieldPopulator.populateObjectFields( album.getArtist(), selector.getField( fieldName ) );
        }
        else if ( fieldName.equals( "songs" ) )
        {
            return _songFieldPopulator.populateListFields( album.getSongs(), selector.getField( fieldName ) );
        }
        return null;
    }
}
