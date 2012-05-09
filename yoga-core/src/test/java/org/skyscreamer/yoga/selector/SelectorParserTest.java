package org.skyscreamer.yoga.selector;

import org.junit.Assert;
import org.junit.Test;

public class SelectorParserTest
{
    @Test
    public void testGDataSimpleSelector() throws Exception
    {
        String selectorExpression = "gender,country";
        Selector selector = new GDataSelectorParser().parseSelector( selectorExpression );
        testSimpleSelector( selector );
    }

    @Test
    public void testGDataNestedSelectors() throws Exception
    {
        String selectorExpression = "gender,favoriteArtists(birthday,discography(year,title)),friends";
        Selector selector = new GDataSelectorParser().parseSelector( selectorExpression );
        testNestedSelectors( selector );
    }

    @Test
    public void testLinkedInSimpleSelector() throws Exception
    {
        String selectorExpression = ":(gender,country)";
        Selector selector = new LinkedInSelectorParser().parseSelector( selectorExpression );
        testSimpleSelector( selector );
    }

    @Test
    public void testLinkedInNestedSelectors() throws Exception
    {
        String selectorExpression = ":(gender,favoriteArtists:(birthday,discography:(year,title)),friends)";
        Selector selector = new LinkedInSelectorParser().parseSelector( selectorExpression );
        testNestedSelectors( selector );
    }

    private void testSimpleSelector( Selector selector ) throws Exception
    {
        Assert.assertEquals( selector.getFields().size(), 2 );
        Selector genderField = selector.getField( "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFields().size(), 0 );

        Selector countryField = selector.getField( "country" );
        Assert.assertNotNull( countryField );
        Assert.assertEquals( countryField.getFields().size(), 0 );
    }

    private void testNestedSelectors( Selector selector ) throws Exception
    {
        Assert.assertEquals( selector.getFields().size(), 3 );
        Selector genderField = selector.getField( "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFields().size(), 0 );

        Selector favoriteArtistsField = selector.getField( "favoriteArtists" );
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
        Selector friendsField = selector.getField( "friends" );
        Assert.assertNotNull( friendsField );
        Assert.assertEquals( friendsField.getFields().size(), 0 );
    }
}
