package org.skyscreamer.yoga.demo.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 4:47 PM
 */
@Entity
@URITemplate("/user/{id}")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class User
{
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "friend", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "friendid"))
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<User> friends = new HashSet<User>();

    @ManyToMany
    @JoinTable(name = "fan", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "artistid"))
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Artist> favoriteArtists = new HashSet<Artist>();

    public boolean getIsFriend()
    {
        // To be implemented
        return true;
    }

    public void setIsFriend( boolean isFriend )
    {
        // to be implemented
    }

    @Core
    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    @Core
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Set<User> getFriends()
    {
        return friends;
    }

    public void setFriends( Set<User> friends )
    {
        this.friends = friends;
    }

    public Set<Artist> getFavoriteArtists()
    {
        return favoriteArtists;
    }

    public void setFavoriteArtists( Set<Artist> favoriteArtists )
    {
        this.favoriteArtists = favoriteArtists;
    }
}
