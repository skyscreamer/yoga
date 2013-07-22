package org.skyscreamer.yoga.test.model.extended;

import java.util.List;

/**
 * User: corby
 * Date: 5/6/12
 */
public class Album
{
    private int _id;
    private String _title;
    private int _year;
    private Artist _artist;
    private List<Song> _songs;

    public Album( int id, String title, int year, Artist artist, List<Song> songs )
    {
        _id = id;
        _title = title;
        _year = year;
        _artist = artist;
        _songs = songs;
    }

    public int getId()
    {
        return _id;
    }

    public String getTitle()
    {
        return _title;
    }

    public int getYear()
    {
        return _year;
    }

    public Artist getArtist()
    {
        return _artist;
    }

    public List<Song> getSongs()
    {
        return _songs;
    }
}
