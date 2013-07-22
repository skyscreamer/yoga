package org.skyscreamer.yoga.demo.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 4:56 PM
 */
@Entity
@URITemplate("/song/{id}")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Song
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "artistId")
    private Artist artist;
    
    @ManyToOne
    @JoinColumn(name = "albumId")
    private Album album;

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
    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public void setArtist( Artist artist )
    {
        this.artist = artist;
    }

    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum( Album album )
    {
        this.album = album;
    }
}
