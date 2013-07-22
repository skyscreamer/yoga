package org.skyscreamer.yoga.test.model.extended;

/**
 * User: corby
 * Date: 5/6/12
 */
public class Song
{
    private int _id;
    private String _title;
    private Album _album;

    public Song( int id, String title, Album album )
    {
        _id = id;
        _title = title;
        _album = album;
    }

    public int getId()
    {
        return _id;
    }

    public String getTitle()
    {
        return _title;
    }

    public Album getAlbum()
    {
        return _album;
    }
}
