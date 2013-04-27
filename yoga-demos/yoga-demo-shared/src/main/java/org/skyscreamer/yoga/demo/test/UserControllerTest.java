package org.skyscreamer.yoga.demo.test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Collections;
import java.util.Map;

import static org.skyscreamer.yoga.demo.test.TestUtil.getJSONArray;
import static org.skyscreamer.yoga.demo.test.TestUtil.getJSONObject;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
public class UserControllerTest
{
    @Test
    public void testGetUser() throws Exception
    {
        JSONObject data = getJSONObject( "/user/2", null );
        String expected = "{id:2,name:\"Corby Page\","
                + "navigationLinks:{friends:{name:\"friends\",href:\"/user/friends/2.json\"},"
                + "favoriteArtists:{name:\"favoriteArtists\",href:\"/user/favoriteArtists/2.json\"},"
                + "isFriend:{name:\"isFriend\",href:\"/user/isFriend/2.json\"}}}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testGetNonExistentUser() throws Exception
    {
        try
        {
            getJSONObject( "/user/8675309", null );
            Assert.fail( "HttpServerErrorException expected." );
        }
        catch ( Exception e )
        {
//            Assert.assertEquals( HttpStatus.NOT_FOUND, e.getStatusCode() );
        }
    }

    @Test
    public void testGetUsers() throws Exception
    {
        JSONArray data = getJSONArray( "/user", null );
        Assert.assertEquals( 1003, data.length() );
        // TODO: figure out how to do this with RESTEasy
//        long count = Long.valueOf(getContent("/user/count", null));
//        Assert.assertEquals( count, data.length() );
    }

    @Test
    public void testGetUserWithSelector() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "isFriend" );
        JSONObject data = getJSONObject( "/user/1", params );
        String expected = "{id:1,name:\"Carter Page\",href:\"/user/1.json\",isFriend:true}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testGetUserWithFriends() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "friends" );
        JSONObject data = getJSONObject( "/user/1", params );
        String expected = "{id:1,name:\"Carter Page\",href:\"/user/1.json\"," + "friends:[{id:2,name:\"Corby Page\",href:\"/user/2.json\"},"
                + "{id:3,name:\"Solomon Duskis\",href:\"/user/3.json\"}]}";
        System.out.println( data );
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testDeepDiveSelector() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "isFriend,friends(favoriteArtists(albums(songs)))" );
        JSONObject data = getJSONObject( "/user/1", params );
        System.out.println( data );
        String expected =
                "{id:1,name:\"Carter Page\",href:\"/user/1.json\",isFriend:true,"
                        + "friends:[{id:2,name:\"Corby Page\",href:\"/user/2.json\","
                        + "favoriteArtists:[{id:2,name:\"Prince\",albums:[{id:4,title:\"1999\","
                        + "songs:[{id:10},{id:11,title:\"Little Red Corvette\"},{id:12}]},{id:5},{id:6}]},{id:1}]}," + "{id:3}]}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testRecommendedAlbums() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "recommendedAlbums" );
        JSONObject data = getJSONObject( "/user/1", params );
        JSONArray recommended = data.getJSONArray( "recommendedAlbums" );
        Assert.assertNotNull( recommended );
    }

    // This should retrieve a LOT of data and throw EntityCountExceededException
    @Test
    public void testGetTooMuchData() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "friends(favoriteArtists)" );
        try
        {
            getJSONObject( "/user", params );
        }
        catch ( Exception e )
        {
            String message = e.getMessage();
            Assert.assertNotNull( message );
//            Assert.assertTrue( message.toLowerCase().contains( "exceeded" ) );
            return;
        } 
        Assert.fail( "Expected this query to fail with a 500 error caused by an EntityCountExceededException" );
    }
}
