package org.skyscreamer.yoga.test.model.extended;

import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * User: corby
 * Date: 5/13/12
 */
public class UserEntityConfigurationWithArtistCoreField extends YogaEntityConfiguration<User>
{
    @Override
    public List<String> getCoreFields()
    {
        return Arrays.asList( "id", "favoriteArtists" );
    }
}
