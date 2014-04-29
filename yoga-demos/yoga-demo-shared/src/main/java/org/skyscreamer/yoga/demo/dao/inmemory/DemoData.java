package org.skyscreamer.yoga.demo.dao.inmemory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.demo.model.User;

import com.google.common.collect.Maps;

public class DemoData
{
    private Map<Long, User> users = Maps.newHashMap();
    private Map<Long, Artist> artists = Maps.newHashMap();
    private Map<Long, Album> albums = Maps.newHashMap();
    private Map<Long, Song> songs = Maps.newHashMap();

    public static final String remoteData = "https://raw.github.com/skyscreamer/skyscreamer.github.com/master/yoga/loaddb.sql.gz";
    public static final String localData = "sampledb.sql";

    public void init()
    {
        try
        {
            init( Thread.currentThread().getContextClassLoader().getResourceAsStream( "sampledb.sql" ) );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
        // this doesn't seem to work yet. It reads a partial line and throws up
//        try
//        {
//            init( new GZIPInputStream( new URL( remoteData ).openStream() ) );
//        }
//        catch ( Exception e )
//        {
//            e.printStackTrace();
//        }
    }

    private void init( InputStream is ) throws IOException
    {
        BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
        String line = null;
        while ( ( line = reader.readLine() ) != null )
        {
            String type = line.replaceFirst( "INSERT INTO ([^(]+).*", "$1" );
            String[] values = line.replaceFirst( ".*VALUES\\((.*)\\)", "$1" ).split( ", " );
            if ("User".equalsIgnoreCase( type ))
            {
                newUser( toLong( values[ 0 ] ), toStr( values[ 1 ] ) );
            }
            else if ("Friend".equalsIgnoreCase( type ))
            {
                newFriend( toLong( values[ 0 ] ), toLong( values[ 1 ] ) );
            }
            else if ("Artist".equalsIgnoreCase( type ))
            {
                newArtist( toLong( values[ 0 ] ), toStr( values[ 1 ] ) );
            }
            else if ("Fan".equalsIgnoreCase( type ))
            {
                newFan( toLong( values[ 0 ] ), toLong( values[ 1 ] ) );
            }
            else if ("Album".equalsIgnoreCase( type ))
            {
                newAlbum( toLong( values[ 0 ] ), toStr( values[ 1 ] ), toLong( values[ 2 ] ),
                        new Integer( values[ 3 ].trim() ) );
            }
            else if ("Song".equalsIgnoreCase( type ))
            {
                newSong( toLong( values[ 0 ] ), toStr( values[ 1 ] ), toLong( values[ 2 ] ), toLong( values[ 3 ] ) );
            }
        }

        is.close();
    }

    private long toLong( String string )
    {
        return Long.parseLong( string.trim() );
    }

    private String toStr( String string )
    {
        return string.trim().replaceFirst( "^'", "" ).replaceFirst( "'$", "" );
    }

    private void newUser( long id, String name )
    {
        User user = new User();
        user.setName( name );
        user.setId( id );
        users.put( id, user );
    }

    private void newFriend( long from, long to )
    {
        users.get( from ).getFriends().add( users.get( to ) );
    }

    private void newArtist( long id, String name )
    {
        Artist artist = new Artist();
        artist.setId( id );
        artist.setName( name );
        artists.put( id, artist );
    }

    private void newFan( long userId, long artistId )
    {
        Artist artist = artists.get( artistId );
        User user = users.get( userId );
        user.getFavoriteArtists().add( artist );
        artist.getFans().add( user );
    }

    private void newAlbum( long id, String title, long artistId, int year )
    {
        Album album = new Album();
        album.setId( id );
        album.setTitle( title );
        Artist artist = artists.get( artistId );
        album.setArtist( artist );
        album.setYear( year );
        albums.put( id, album );
        artist.getAlbums().add( album );
    }

    private void newSong( long id, String title, long artistId, long albumId )
    {
        Song song = new Song();
        song.setId( id );
        song.setTitle( title );
        song.setArtist( artists.get( artistId ) );
        Album album = albums.get( albumId );
        song.setAlbum( album );
        album.getSongs().add( song );
        songs.put( id, song );
    }

    @SuppressWarnings( "unchecked" )
    protected <T> Map<Long, T> getMap( Class<T> type )
    {
        if (type == User.class)
            return ( Map<Long, T> ) users;
        if (type == Album.class)
            return ( Map<Long, T> ) albums;
        if (type == Song.class)
            return ( Map<Long, T> ) songs;
        if (type == Artist.class)
            return ( Map<Long, T> ) artists;
        else
            throw new IllegalArgumentException( "you cannot get a map for type: " + type.getName() );
    }

    public <T> T get( Class<T> type, long id )
    {
        return getMap( type ).get( id );
    }

    public <T> Collection<T> getAll( Class<T> type )
    {
        return getMap( type ).values();
    }
}
