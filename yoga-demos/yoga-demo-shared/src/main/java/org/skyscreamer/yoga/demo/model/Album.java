package org.skyscreamer.yoga.demo.model;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.URITemplate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/11/11 Time: 4:53 PM
 */
@Entity
@URITemplate("/album/{id}")
public class Album {
	@Id
	@GeneratedValue
	private long id;
	private String title;
	@ManyToOne
	@JoinColumn(name = "artistId")
	private Artist artist;
	private int year;
	@OneToMany(mappedBy = "album")
	@OrderBy("id")
	private List<Song> songs;

	@Core
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
