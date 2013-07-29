package org.skyscreamer.yoga.demo.test.jersey.standalone;

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

    public DemoData()
    {
        init();
    }

    private void init()
    {
        newUser( 1, "Carter Page" );
        newUser( 2, "Corby Page" );
        newUser( 3, "Solomon Duskis" );
        newFriend( 1, 2 );
        newFriend( 2, 1 );
        newFriend( 1, 3 );
        newFriend( 3, 1 );
        newFriend( 2, 3 );
        newFriend( 3, 2 );
        newArtist( 1, "Arcade Fire" );
        newArtist( 2, "Prince" );
        newArtist( 3, "Neutral Milk Hotel" );
        newArtist( 4, "Eighth Day" );
        newArtist( 5, "Kansas" );
        newFan( 1, 1 );
        newFan( 1, 3 );
        newFan( 2, 1 );
        newFan( 2, 2 );
        newFan( 3, 4 );
        newFan( 3, 5 );
        newAlbum( 1, "Funeral", 1, 2004 );
        newAlbum( 2, "Neon Bible", 1, 2007 );
        newAlbum( 3, "The Suburbs", 1, 2010 );
        newAlbum( 4, "1999", 2, 1982 );
        newAlbum( 5, "Purple Rain", 2, 1984 );
        newAlbum( 6, "Diamonds and Pearls", 2, 1991 );
        newAlbum( 7, "On Avery Island", 3, 1996 );
        newAlbum( 8, "In the Aeroplane over the Sea", 3, 1998 );
        newAlbum( 9, "Chasing Prophecy", 4, 2011 );
        newAlbum( 10, "Leftoverture", 5, 1976 );
        newSong( 1, "Neighborhood #1 (Tunnels)", 1, 1 );
        newSong( 2, "Wake Up", 1, 1 );
        newSong( 3, "Haiti", 1, 1 );
        newSong( 4, "Black Mirror", 1, 2 );
        newSong( 5, "Neon Bible", 1, 2 );
        newSong( 6, "No Cars Go", 1, 2 );
        newSong( 7, "Ready to Start", 1, 3 );
        newSong( 8, "Rococo", 1, 3 );
        newSong( 9, "Suburban War", 1, 3 );
        newSong( 10, "1999", 2, 4 );
        newSong( 11, "Little Red Corvette", 2, 4 );
        newSong( 12, "Let\"s Pretend We\"re Married", 2, 4 );
        newSong( 13, "Let\"s Go Crazy", 2, 5 );
        newSong( 14, "When Doves Cry", 2, 5 );
        newSong( 15, "Purple Rain", 2, 5 );
        newSong( 16, "Thunder", 2, 6 );
        newSong( 17, "Cream", 2, 6 );
        newSong( 18, "Gett Off", 2, 6 );
        newSong( 19, "Where You\"ll Find Me Now", 3, 7 );
        newSong( 20, "Naomi", 3, 7 );
        newSong( 21, "Pree-Sisters Swallowing a Donkey\"s Eye", 3, 7 );
        newSong( 22, "The King of Carrot Flowers Pt. One", 3, 8 );
        newSong( 23, "Two-Headed Boy", 3, 8 );
        newSong( 24, "Oh Comely", 3, 8 );
        newSong( 25, "Ya\"alili", 4, 9 );
        newSong( 26, "Avraham", 4, 9 );
        newSong( 27, "Yiddishe Mama", 4, 9 );
        newSong( 28, "Carry On Wayward Son", 5, 10 );
        newSong( 29, "The Wall", 5, 10 );
        newSong( 30, "Miracles Out of Nowhere", 5, 10 );

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
        users.get( userId ).getFavoriteArtists().add( artists.get( artistId ) );
    }

    private void newAlbum( long id, String title, long artistId, int year )
    {
        Album album = new Album();
        album.setId( id );
        album.setTitle( title );
        album.setArtist( artists.get( artistId ) );
        album.setYear( year );
        albums.put( id, album );
    }

    private void newSong( long id, String title, long artistId, long albumId )
    {
        Song song = new Song();
        song.setId( id );
        song.setTitle( title );
        song.setArtist( artists.get( artistId ) );
        song.setAlbum( albums.get( albumId ) );
        songs.put( id, song );
    }

    @SuppressWarnings( "unchecked" )
    protected <T> Map<Long, T> getMap( Class<T> type )
    {
        if( type == User.class) return ( Map<Long, T> ) users;
        if( type == Album.class ) return ( Map<Long, T> ) albums;
        if( type == Song.class) return ( Map<Long, T> ) songs;
        if( type == Artist.class) return ( Map<Long, T> ) artists;
        else throw new IllegalArgumentException( "you cannot get a map for type: " + type.getName() );
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
