package org.skyscreamer.yoga.selector;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.LinkedInSelectorParser;

public class SelectorParserTest
{
    @Test
    public void testGDataSimpleSelector() throws Exception
    {
        String selectorExpression = "gender,country";
        FieldSelector selector = new GDataSelectorParser().parseSelector( selectorExpression );
        testSimpleSelector( selector );
    }

    @Test
    public void testGDataNestedSelectors() throws Exception
    {
        String selectorExpression = "gender,favoriteArtists(birthday,discography(year,title)),friends";
        FieldSelector selector = new GDataSelectorParser().parseSelector( selectorExpression );
        testNestedSelectors( selector );
    }

    @Test
    public void testLinkedInSimpleSelector() throws Exception
    {
        String selectorExpression = ":(gender,country)";
        FieldSelector selector = new LinkedInSelectorParser().parseSelector( selectorExpression );
        testSimpleSelector( selector );
    }

    @Test
    public void testLinkedInNestedSelectors() throws Exception
    {
        String selectorExpression = ":(gender,favoriteArtists:(birthday,discography:(year,title)),friends)";
        FieldSelector selector = new LinkedInSelectorParser().parseSelector( selectorExpression );
        testNestedSelectors( selector );
    }

    private void testSimpleSelector( FieldSelector selector ) throws Exception
    {
        Assert.assertEquals( selector.getFieldCount(), 2 );
        FieldSelector genderField = selector.getSelector( "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFieldCount(), 0 );

        FieldSelector countryField = selector.getSelector( "country" );
        Assert.assertNotNull( countryField );
        Assert.assertEquals( countryField.getFieldCount(), 0 );
    }

    private void testNestedSelectors( FieldSelector selector ) throws Exception
    {
        Assert.assertEquals( selector.getFieldCount(), 3 );
        FieldSelector genderField = selector.getChildSelector( null, "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getFieldCount(), 0 );

        FieldSelector favoriteArtistsField = selector.getChildSelector( null, "favoriteArtists" );
        Assert.assertNotNull( favoriteArtistsField );
        Assert.assertEquals( favoriteArtistsField.getFieldCount(), 2 );

        FieldSelector birthdayField = favoriteArtistsField.getChildSelector( null, "birthday" );
        Assert.assertNotNull( birthdayField );
        Assert.assertEquals( birthdayField.getFieldCount(), 0 );

        FieldSelector discographyField = favoriteArtistsField.getChildSelector( null, "discography" );
        Assert.assertNotNull( discographyField );
        Assert.assertEquals( discographyField.getFieldCount(), 2 );

        FieldSelector yearField = discographyField.getChildSelector( null, "year" );
        Assert.assertNotNull( yearField );
        Assert.assertEquals( yearField.getFieldCount(), 0 );

        FieldSelector titleField = discographyField.getChildSelector( null, "title" );
        Assert.assertNotNull( titleField );
        Assert.assertEquals( titleField.getFieldCount(), 0 );

        Assert.assertEquals( selector.getFieldCount(), 3 );
        FieldSelector friendsField = selector.getChildSelector( null, "friends" );
        Assert.assertNotNull( friendsField );
        Assert.assertEquals( friendsField.getFieldCount(), 0 );
    }
}
