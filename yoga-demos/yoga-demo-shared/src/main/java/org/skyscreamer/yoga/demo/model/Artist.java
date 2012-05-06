package org.skyscreamer.yoga.demo.model;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 4:49 PM
 */
@Entity
@URITemplate("/artist/{id}")
public class Artist
{
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "artist")
    private List<Album> albums;
    @ManyToMany(mappedBy = "favoriteArtists")
    private Set<User> fans;

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
