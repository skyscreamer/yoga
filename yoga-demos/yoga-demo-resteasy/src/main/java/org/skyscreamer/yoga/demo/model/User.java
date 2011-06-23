package org.skyscreamer.yoga.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.jboss.resteasy.spi.touri.MappedBy;
import org.skyscreamer.yoga.demo.controller.UserController;
import org.skyscreamer.yoga.resteasy.annotations.Attribute;
import org.skyscreamer.yoga.selector.Core;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/11/11
 * Time: 4:47 PM
 */
@Entity
@MappedBy(resource=UserController.class, method="get")
public class User
{
    @Id @GeneratedValue private long id;
    private String name;

    @ManyToMany
    @JoinTable(name="friend", joinColumns=@JoinColumn(name="userid"), inverseJoinColumns=@JoinColumn(name="friendid"))
    private Set<User> friends = new HashSet<User>();

    @ManyToMany
    @JoinTable(name="fan", joinColumns=@JoinColumn(name="userid"), inverseJoinColumns=@JoinColumn(name="artistid"))
    private Set<Artist> favoriteArtists = new HashSet<Artist>();

    public boolean getIsFriend() {
        // To be implemented
        return false;
    }

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

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Artist> getFavoriteArtists() {
        return favoriteArtists;
    }

    public void setFavoriteArtists(Set<Artist> favoriteArtists) {
        this.favoriteArtists = favoriteArtists;
    }
}
