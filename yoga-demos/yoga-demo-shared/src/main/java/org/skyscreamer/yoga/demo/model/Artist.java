package org.skyscreamer.yoga.demo.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 4:49 PM
 */
@Entity
@URITemplate("/artist/{id}")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Artist
{
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "artist")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<Album> albums = new ArrayList<Album>();

    @ManyToMany(mappedBy = "favoriteArtists")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<User> fans = new HashSet<User>();

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

    public List<Album> getAlbums()
    {
        return albums;
    }

    public void setAlbums( List<Album> albums )
    {
        this.albums = albums;
    }

    public Set<User> getFans()
    {
        return fans;
    }

    public void setFans( Set<User> fans )
    {
        this.fans = fans;
    }

}
