package org.skyscreamer.yoga.test.model.extended;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import java.util.Set;

/**
 * User: corby
 * Date: 5/6/12
 */
@URITemplate( "/user/{id}" )
public class User
{
    private int _id;
    private String _name;
    private String _email;

    private Set<User> _friends;
    private Set<Artist> _favoriteArtists;

    public User( int id, String name, String email, Set<User> friends, Set<Artist> favoriteArtists )
    {
        _id = id;
        _name = name;
        _email = email;
        _friends = friends;
        _favoriteArtists = favoriteArtists;
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

    public String getEmail()
    {
        return _email;
    }

    public Set<User> getFriends()
    {
        return _friends;
    }

    public Set<Artist> getFavoriteArtists()
    {
        return _favoriteArtists;
    }
}
