package org.skyscreamer.yoga.demo.dto;

import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.selector.Core;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public class UserDto
{
    private long _id;
    private String _name;
    private List<Album> _recommendedAlbums;

    public UserDto()
    {
    }

    @Core
    public long getId()
    {
        return _id;
    }

    public void setId( long id )
    {
        _id = id;
    }

    @Core
    public String getName()
    {
        return _name;
    }

    public void setName( String name )
    {
        _name = name;
    }

    public List<Album> getRecommendedAlbums()
    {
        return _recommendedAlbums;
    }

    public void setRecommendedAlbums( List<Album> recommendedAlbums )
    {
        _recommendedAlbums = recommendedAlbums;
    }
}
