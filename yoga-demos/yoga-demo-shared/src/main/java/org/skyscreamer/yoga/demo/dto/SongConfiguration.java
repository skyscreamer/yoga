package org.skyscreamer.yoga.demo.dto;

import java.util.Arrays;
import java.util.Collection;

import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.selector.NamedProperty;
import org.skyscreamer.yoga.selector.Property;

public class SongConfiguration extends YogaEntityConfiguration<Song>
{

    @SuppressWarnings("unchecked")
    @Override
	public Collection<Property<Song>> getProperties()
	{
		return Arrays.asList(createIdProperty(), createTitleProperty(), createArtistProperty(), createAlbumProperty());
	}

	private Property<Song> createArtistProperty()
    {
	    return new NamedProperty<Song>("artist", false)
		{
			@Override
			public Artist getValue(Song Song)
			{
				return Song.getArtist();
			}
		};
    }

	private Property<Song> createTitleProperty()
    {
	    return new NamedProperty<Song>("title", true)
		{
			@Override
			public String getValue(Song Song)
			{
				return Song.getTitle();
			}
		};
    }


	private Property<Song> createIdProperty()
    {
	    return new NamedProperty<Song>("id", true)
		{
			@Override
			public Long getValue(Song Song)
			{
				return Song.getId();
			}
		};
    }

	private Property<Song> createAlbumProperty()
    {
	    return new NamedProperty<Song>("album", false)
		{
			@Override
			public Album getValue(Song Song)
			{
				return Song.getAlbum();
			}
		};
    }

}
