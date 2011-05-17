package org.skyscreamer.yoga.demo.model;

import org.jboss.resteasy.spi.touri.MappedBy;
import org.skyscreamer.yoga.demo.annotations.Attribute;
import org.skyscreamer.yoga.demo.controller.ArtistController;
import org.skyscreamer.yoga.selector.Core;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/11/11
 * Time: 4:49 PM
 */
@Entity
@MappedBy(resource=ArtistController.class, method="get")
public class Artist {
    @Id @GeneratedValue private long id;
    private String name;
    @OneToMany(mappedBy = "artist") private List<Album> albums;
    @ManyToMany(mappedBy = "favoriteArtists") private Set<User> fans;

    @Core
    @Attribute
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Core
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public Set<User> getFans() {
        return fans;
    }

    public void setFans(Set<User> fans) {
        this.fans = fans;
    }

}
