package org.skyscreamer.yoga.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.jboss.resteasy.spi.touri.URITemplate;
import org.skyscreamer.yoga.demo.annotations.Attribute;
import org.skyscreamer.yoga.selector.Core;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/11/11
 * Time: 4:53 PM
 */
@Entity
@URITemplate("/album/{id}")
public class Album {
    @Id @GeneratedValue private long id;
    private String title;
    @ManyToOne @JoinColumn(name = "artistId") private Artist artist;
    private int year;
    @OneToMany(mappedBy = "album") @OrderBy("id") private List<Song> songs;

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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Core
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
