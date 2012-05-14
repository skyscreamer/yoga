package org.skyscreamer.yoga.test.model.extended;

import org.skyscreamer.yoga.annotations.PopulationExtension;

import java.util.Arrays;
import java.util.List;

/**
 * User: corby
 * Date: 5/13/12
 */
@PopulationExtension(User.class)
public class UserFieldPopulatorWithArtistCoreField
{
    public List<String> getCoreFields()
    {
        return Arrays.asList( "id", "favoriteArtists" );
    }
}
