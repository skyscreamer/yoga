package org.skyscreamer.yoga.test.model.extended;

import org.skyscreamer.yoga.annotations.Core;

import java.util.List;
import java.util.Set;

/**
 * User: corby
 * Date: 5/6/12
 */
public class Artist
{
    private int _id;
    private String _name;
    private List<Album> _albums;
    private Set<User> _fans;

    public Artist( int id, String name, List<Album> albums, Set<User> fans )
    {
        _id = id;
        _name = name;
        _albums = albums;
        _fans = fans;
    }

    @Core
    public int getId()
    {
        return _id;
    }

    @Core
    public String getName()
    {
        return _name;
    }

    public List<Album> getAlbums()
    {
        return _albums;
    }

    public Set<User> getFans()
    {
        return _fans;
    }
}
