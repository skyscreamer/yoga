package org.skyscreamer.yoga.test.model.extended;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;

import java.util.Arrays;
import java.util.List;

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
