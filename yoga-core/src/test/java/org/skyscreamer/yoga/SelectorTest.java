package org.skyscreamer.yoga;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.selector.LinkedInSelectorParser;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;

/**
 * Created by IntelliJ IDEA.
 * User: cpage
 * Date: 4/9/11
 * Time: 4:23 PM
 */
public class SelectorTest
{
    private SelectorParser _selectorParser = new LinkedInSelectorParser();

    @Test
    public void testSimpleSelector() throws Exception
    {
        Selector selector = _selectorParser.parse( ":(gender,country)" );

        Assert.assertEquals(selector.getFields().size(), 2);
        Selector genderField = selector.getField( "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFields().size(), 0 );

        Selector countryField = selector.getField( "country" );
        Assert.assertNotNull( countryField );
        Assert.assertEquals( countryField.getFields().size(), 0 );
    }

    @Test
    public void testNestedSelectors() throws Exception
    {
        Selector selector = _selectorParser.parse( ":(gender,favoriteArtists:(birthday,discography:(year,title)),friends)" );

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
