package org.skyscreamer.yoga.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jboss.resteasy.spi.touri.MappedBy;
import org.skyscreamer.yoga.demo.annotations.Attribute;
import org.skyscreamer.yoga.demo.annotations.Reference;
import org.skyscreamer.yoga.demo.controller.SongController;
import org.skyscreamer.yoga.selector.Core;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/11/11
 * Time: 4:56 PM
 */
@Entity
@MappedBy(resource=SongController.class, method="get")
public class Song {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;
    private String title;
    @ManyToOne @JoinColumn(name = "artistId") private Artist artist;
    @ManyToOne @JoinColumn(name = "albumId") private Album album;

    @Core
    @Attribute
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Core
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Core
    @Reference(selector=":(name)")
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
