package org.skyscreamer.yoga;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorField;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: cpage
 * Date: 4/9/11
 * Time: 4:23 PM
 */
public class SelectorTest
{
    @Test
    public void testSimpleSelector() throws Exception
    {
        Selector selector = new Selector( ":(gender,country)" );

        Assert.assertEquals(selector.getFields().size(), 2);
        SelectorField genderField = findFieldByName( selector.getFields(), "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getSelector().getFields().size(), 0 );

        SelectorField countryField = findFieldByName( selector.getFields(), "country" );
        Assert.assertNotNull( countryField );
        Assert.assertEquals( countryField.getSelector().getFields().size(), 0 );
    }

    @Test
    public void testNestedSelectors() throws Exception
    {
        Selector selector = new Selector( ":(gender,favoriteArtists:(birthday,discography:(year,title)),friends)" );

        Assert.assertEquals( selector.getFields().size(), 3 );
        SelectorField genderField = findFieldByName( selector.getFields(), "gender" );
        Assert.assertNotNull( genderField );
        Assert.assertEquals( genderField.getSelector().getFields().size(), 0 );

        SelectorField favoriteArtistsField = findFieldByName( selector.getFields(), "favoriteArtists" );
        Assert.assertNotNull( favoriteArtistsField );
        Assert.assertEquals( favoriteArtistsField.getSelector().getFields().size(), 2 );

        SelectorField birthdayField = findFieldByName( favoriteArtistsField.getSelector().getFields(), "birthday" );
        Assert.assertNotNull( birthdayField );
        Assert.assertEquals( birthdayField.getSelector().getFields().size(), 0 );

        SelectorField discographyField = findFieldByName( favoriteArtistsField.getSelector().getFields(), "discography" );
        Assert.assertNotNull( discographyField );
        Assert.assertEquals( discographyField.getSelector().getFields().size(), 2 );

        SelectorField yearField = findFieldByName( discographyField.getSelector().getFields(), "year" );
        Assert.assertNotNull( yearField );
        Assert.assertEquals( yearField.getSelector().getFields().size(), 0 );

        SelectorField titleField = findFieldByName( discographyField.getSelector().getFields(), "title" );
        Assert.assertNotNull( titleField );
        Assert.assertEquals( titleField.getSelector().getFields().size(), 0 );

        Assert.assertEquals( selector.getFields().size(), 3 );
        SelectorField friendsField = findFieldByName( selector.getFields(), "friends" );
        Assert.assertNotNull( friendsField );
        Assert.assertEquals( friendsField.getSelector().getFields().size(), 0 );
    }

    private SelectorField findFieldByName( Set<SelectorField> fields, String name )
    {
        SelectorField result = null;
        for( SelectorField field : fields )
        {
            if ( field.getFieldName().equals( name ) )
            {
                result = field;
            }
        }
        return result;
    }
}
