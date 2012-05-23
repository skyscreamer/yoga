package org.skyscreamer.yoga.test.model.extended;

import org.skyscreamer.yoga.annotations.CoreFields;
import org.skyscreamer.yoga.annotations.PopulationExtension;
import org.skyscreamer.yoga.annotations.SupportedFields;

import java.util.Arrays;
import java.util.List;

/**
 * User: corby
 * Date: 5/13/12
 */
@PopulationExtension(Album.class)
public class AlbumFieldPopulator
{
    @CoreFields
    public List<String> getCoreFields()
    {
        return Arrays.asList( "id", "title" );
    }

    @SupportedFields
    public List<String> getSupportedFields()
    {
        return Arrays.asList( "artist", "songs" );
    }

    public String getURITemplate()
    {
        return "/album/{id}";
    }
}
