package org.skyscreamer.yoga.demo.dto;

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
@Component( "songFieldPopulator" )
public class SongFieldPopulator extends AbstractFieldPopulator<Song>
{
    @Autowired @Qualifier("artistFieldPopulator") FieldPopulator<Artist> _artistFieldPopulator;
    @Autowired @Qualifier("albumFieldPopulator") FieldPopulator<Album> _albumFieldPopulator;

    protected Collection<String> getCoreFieldNames()
    {
        return Arrays.asList( "id", "title" );
    }

    protected Collection<String> getModelFieldNames()
    {
        return Arrays.asList( "id", "title" );
    }

    protected Object constructFieldValue( String fieldName, Song song, Selector selector )
    {
        if ( fieldName.equals( "artist" ) )
        {
            return _artistFieldPopulator.populateObjectFields( song.getArtist(), selector.getField( fieldName ) );
        }
        else if ( fieldName.equals( "album" ) )
        {
            return _albumFieldPopulator.populateObjectFields( song.getAlbum(), selector.getField( fieldName ) );
        }
        return null;
    }
}
