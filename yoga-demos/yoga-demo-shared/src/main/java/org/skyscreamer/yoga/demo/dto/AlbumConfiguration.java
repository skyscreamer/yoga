package org.skyscreamer.yoga.demo.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.selector.NamedProperty;
import org.skyscreamer.yoga.selector.Property;

public class AlbumConfiguration extends YogaEntityConfiguration<Album>
{

    @SuppressWarnings("unchecked")
    @Override
	public Collection<Property<Album>> getProperties()
	{
		return Arrays.asList(createIdProperty(), createYearProperty(), createSongsProperty(), createTitleProperty(), createArtistProperty());
	}

	private Property<Album> createArtistProperty()
    {
	    return new NamedProperty<Album>("artist", false)
		{
			@Override
			public Artist getValue(Album album)
			{
				return album.getArtist();
			}
		};
    }

	private Property<Album> createTitleProperty()
    {
	    return new NamedProperty<Album>("title", true)
		{
			@Override
			public String getValue(Album album)
			{
				return album.getTitle();
			}
		};
    }

	private Property<Album> createYearProperty()
    {
	    return new NamedProperty<Album>("year", true)
		{
			@Override
			public Integer getValue(Album album)
			{
				return album.getYear();
			}
		};
    }

	private Property<Album> createIdProperty()
    {
	    return new NamedProperty<Album>("id", true)
		{
			@Override
			public Long getValue(Album album)
			{
				return album.getId();
			}
		};
    }

	private Property<Album> createSongsProperty()
    {
	    return new NamedProperty<Album>("songs", false)
		{
			@Override
			public List<Song> getValue(Album album)
			{
				return album.getSongs();
			}
		};
    }

}
