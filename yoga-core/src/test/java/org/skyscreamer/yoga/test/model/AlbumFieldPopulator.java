package org.skyscreamer.yoga.test.model;

import org.skyscreamer.yoga.annotations.PopulationExtension;
import org.skyscreamer.yoga.populator.FieldPopulatorSupport;

import java.util.Arrays;
import java.util.List;

/**
 * User: corby
 * Date: 5/8/12
 */
@PopulationExtension( Album.class )
public class AlbumFieldPopulator extends FieldPopulatorSupport
{
    public List<String> getCoreFields()
    {
        return Arrays.asList( "id", "title" );
    }
}
