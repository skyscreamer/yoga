package org.skyscreamer.yoga;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.selector.Selector;

/**
 * Shared test logic for selector tests
 *
 * @author Carter Page <carter@skyscreamer.org>
 */
public class AbstractSelectorTest {
    protected void testSimpleSelector(Selector selector) throws Exception
    {
        Assert.assertEquals(selector.getFields().size(), 2);
        Selector genderField = selector.getField( "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFields().size(), 0 );

        Selector countryField = selector.getField( "country" );
        Assert.assertNotNull( countryField );
        Assert.assertEquals( countryField.getFields().size(), 0 );
    }

    protected void testNestedSelectors(Selector selector) throws Exception
    {
        Assert.assertEquals( selector.getFields().size(), 3 );
        Selector genderField = selector.getField(  "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFields().size(), 0 );

        Selector favoriteArtistsField = selector.getField(  "favoriteArtists" );
        Assert.assertNotNull( favoriteArtistsField );
        Assert.assertEquals( favoriteArtistsField.getFields().size(), 2 );

        Selector birthdayField = favoriteArtistsField.getField( "birthday" );
        Assert.assertNotNull( birthdayField );
        Assert.assertEquals( birthdayField.getFields().size(), 0 );

        Selector discographyField = favoriteArtistsField.getField( "discography" );
        Assert.assertNotNull( discographyField );
        Assert.assertEquals( discographyField.getFields().size(), 2 );

        Selector yearField = discographyField.getField( "year" );
        Assert.assertNotNull( yearField );
        Assert.assertEquals( yearField.getFields().size(), 0 );

        Selector titleField = discographyField.getField( "title" );
        Assert.assertNotNull( titleField );
        Assert.assertEquals( titleField.getFields().size(), 0 );

        Assert.assertEquals( selector.getFields().size(), 3 );
        Selector friendsField = selector.getField(  "friends" );
        Assert.assertNotNull( friendsField );
        Assert.assertEquals( friendsField.getFields().size(), 0 );
    }
}
