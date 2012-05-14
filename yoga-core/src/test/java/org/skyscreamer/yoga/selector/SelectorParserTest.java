package org.skyscreamer.yoga.selector;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.LinkedInSelectorParser;

public class SelectorParserTest
{
    @Test
    public void testGDataSimpleSelector() throws Exception
    {
        String selectorExpression = "gender,country";
        Selector selector = new GDataSelectorParser( new DefaultFieldPopulatorRegistry() ).parseSelector( selectorExpression );
        testSimpleSelector( selector );
    }

    @Test
    public void testGDataNestedSelectors() throws Exception
    {
        String selectorExpression = "gender,favoriteArtists(birthday,discography(year,title)),friends";
        Selector selector = new GDataSelectorParser( new DefaultFieldPopulatorRegistry() ).parseSelector( selectorExpression );
        testNestedSelectors( selector );
    }

    @Test
    public void testLinkedInSimpleSelector() throws Exception
    {
        String selectorExpression = ":(gender,country)";
        Selector selector = new LinkedInSelectorParser( new DefaultFieldPopulatorRegistry() ).parseSelector( selectorExpression );
        testSimpleSelector( selector );
    }

    @Test
    public void testLinkedInNestedSelectors() throws Exception
    {
        String selectorExpression = ":(gender,favoriteArtists:(birthday,discography:(year,title)),friends)";
        Selector selector = new LinkedInSelectorParser( new DefaultFieldPopulatorRegistry() ).parseSelector( selectorExpression );
        testNestedSelectors( selector );
    }

    private void testSimpleSelector( Selector selector ) throws Exception
    {
        Assert.assertEquals( selector.getSelectors( null ).size(), 2 );
        Selector genderField = selector.getChildSelector( null, "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getSelectors( null ).size(), 0 );

        Selector countryField = selector.getChildSelector( null, "country" );
        Assert.assertNotNull( countryField );
        Assert.assertEquals( countryField.getSelectors( null ).size(), 0 );
    }

    private void testNestedSelectors( Selector selector ) throws Exception
    {
        Assert.assertEquals( selector.getSelectors( null ).size(), 3 );
        Selector genderField = selector.getChildSelector( null, "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getSelectors( null ).size(), 0 );

        Selector favoriteArtistsField = selector.getChildSelector( null, "favoriteArtists" );
        Assert.assertNotNull( favoriteArtistsField );
        Assert.assertEquals( favoriteArtistsField.getSelectors( null ).size(), 2 );

        Selector birthdayField = favoriteArtistsField.getChildSelector( null, "birthday" );
        Assert.assertNotNull( birthdayField );
        Assert.assertEquals( birthdayField.getSelectors( null ).size(), 0 );

        Selector discographyField = favoriteArtistsField.getChildSelector( null, "discography" );
        Assert.assertNotNull( discographyField );
        Assert.assertEquals( discographyField.getSelectors( null ).size(), 2 );

        Selector yearField = discographyField.getChildSelector( null, "year" );
        Assert.assertNotNull( yearField );
        Assert.assertEquals( yearField.getSelectors( null ).size(), 0 );

        Selector titleField = discographyField.getChildSelector( null, "title" );
        Assert.assertNotNull( titleField );
        Assert.assertEquals( titleField.getSelectors( null ).size(), 0 );

        Assert.assertEquals( selector.getSelectors( null ).size(), 3 );
        Selector friendsField = selector.getChildSelector( null, "friends" );
        Assert.assertNotNull( friendsField );
        Assert.assertEquals( friendsField.getSelectors( null ).size(), 0 );
    }
}
