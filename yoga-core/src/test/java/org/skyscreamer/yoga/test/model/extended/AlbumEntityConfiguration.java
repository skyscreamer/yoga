package org.skyscreamer.yoga.test.model.extended;

import java.util.Arrays;
import java.util.List;

import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;

/**
 * User: corby
 * Date: 5/13/12
 */
public class AlbumEntityConfiguration extends YogaEntityConfiguration<Album>
{
    @Override
    public List<String> getCoreFields()
    {
        return Arrays.asList( "id", "title" );
    }

    @Override
    public List<String> getSelectableFields()
    {
        return Arrays.asList( "artist", "songs" );
    }

    @Override
    public String getURITemplate()
    {
        return "/album/{id}";
    }
}
