package org.skyscreamer.yoga.test.model.basic;

import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.Artist;
import org.skyscreamer.yoga.test.model.extended.Song;
import org.skyscreamer.yoga.test.model.extended.User;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: corby
 * Date: 5/6/12
 */
public class DataGenerator
{
    public static User carter()
    {
        return new User( 1, "Carter Page", "carter@skyscreamer.org", new HashSet<User>(), new HashSet<Artist>() );
    }

    public static User corby()
    {
        return new User( 2, "Corby Page", "corby@skyscreamer.org", new HashSet<User>(), new HashSet<Artist>() );
    }

    public static User solomon()
    {
        return new User( 3, "Solomon Duskis", "solomon@skyscreamer.org", new HashSet<User>(), new HashSet<Artist>() );
    }

    public static Artist arcadeFire()
    {
        return new Artist( 1, "Arcade Fire", new ArrayList<Album>(), new HashSet<User>() );
    }

    public static Artist neutralMilkHotel()
    {
        return new Artist( 2, "Neutral Milk Hotel", new ArrayList<Album>(), new HashSet<User>() );
    }

    public static Artist prince()
    {
        return new Artist( 3, "Prince", new ArrayList<Album>(), new HashSet<User>() );
    }

    public static Artist kansas()
    {
        return new Artist( 4, "Kansas", new ArrayList<Album>(), new HashSet<User>() );
    }

    public static Artist eigthDay()
    {
        return new Artist( 5, "8th Day", new ArrayList<Album>(), new HashSet<User>() );
    }

    public static Album funeral()
    {
        Album album = new Album( 1, "Funeral", 2004, arcadeFire(), new ArrayList<Song>() );
        addSong( album, 1, "Neighborhood #1 (Tunnels)" );
        addSong( album, 2, "Wake Up" );
        addSong( album, 3, "Haiti" );
        return album;
    }

    public static Album neonBible()
    {
        Album album = new Album( 2, "Neon Bible", 2007, arcadeFire(), new ArrayList<Song>() );
        addSong( album, 4, "Black Mirror" );
        addSong( album, 5, "Neon Bible" );
        addSong( album, 6, "No Cars Go" );
        return album;
    }

    public static Album theSuburbs()
    {
        Album album = new Album( 3, "The Suburbs", 2010, arcadeFire(), new ArrayList<Song>() );
        addSong( album, 7, "Ready To Start" );
        addSong( album, 8, "Rococo" );
        addSong( album, 9, "Suburban War" );
        return album;
    }

    public static Album nineteen99()
    {
        Album album = new Album( 4, "1999", 1982, prince(), new ArrayList<Song>() );
        addSong( album, 10, "1999" );
        addSong( album, 11, "Little Red Corvette" );
        addSong( album, 12, "Let's Pretend We're Married" );
        return album;
    }

    public static Album signOfTheTimes()
    {
        Album album = new Album( 5, "Sign O' The Times", 1987, prince(), new ArrayList<Song>() );
        addSong( album, 13, "U Got The Look" );
        addSong( album, 14, "The Cross" );
        addSong( album, 15, "Adore" );
        return album;
    }

    public static Album diamondsAndPearls()
    {
        Album album = new Album( 6, "Diamonds And Pearls", 1991, prince(), new ArrayList<Song>() );
        addSong( album, 16, "Thunder" );
        addSong( album, 17, "Cream" );
        addSong( album, 18, "Gett Off" );
        return album;
    }

    public static Album onAveryIsland()
    {
        Album album = new Album( 7, "On Avery Island", 1996, neutralMilkHotel(), new ArrayList<Song>() );
        addSong( album, 19, "Where You'll Find Me Now" );
        addSong( album, 20, "Naomi" );
        addSong( album, 21, "Pree-Sisters Swallowing a Donkey's Eye" );
        return album;
    }

    public static Album aeroplane()
    {
        Album album = new Album( 8, "In the Aeroplane over the Sea", 1998, neutralMilkHotel(), new ArrayList<Song>() );
        addSong( album, 22, "The King of Carrot Flowers Pt. One" );
        addSong( album, 23, "Two-Headed Boy" );
        addSong( album, 24, "Oh Comely" );
        return album;
    }

    public static Album chasingProphecy()
    {
        Album album = new Album( 9, "Chasing Prophecy", 2011, eigthDay(), new ArrayList<Song>() );
        addSong( album, 25, "Ya'alili" );
        addSong( album, 26, "Avraham" );
        addSong( album, 27, "Yiddishe Mama" );
        return album;
    }

    public static Album leftoverture()
    {
        Album album = new Album( 10, "Leftoverture", 1976, kansas(), new ArrayList<Song>() );
        addSong( album, 28, "Carry On Wayward Son" );
        addSong( album, 29, "The Wall" );
        addSong( album, 30, "Miracles Out of Nowhere" );
        return album;
    }

    private static void addSong( Album album, int id, String title )
    {
        Song song = new Song( id, title, album );
        album.getSongs().add( song );
    }
}
