package org.skyscreamer.yoga.demo.test;

import static org.skyscreamer.yoga.demo.util.TestUtil.getBean;
import static org.skyscreamer.yoga.demo.util.TestUtil.getJSONArray;
import static org.skyscreamer.yoga.demo.util.TestUtil.getJSONObject;

import java.util.Collections;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.User;

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
        Assert.assertEquals( getBean( GenericDao.class ).getCount( User.class ).intValue(), data.length() );
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
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testDeepDiveSelector() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "isFriend,friends(favoriteArtists(albums(songs)))" );
        JSONObject data = getJSONObject( "/user/1", params );
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
    
    @Test
    public void testMetaData() throws Exception
    {
        String expected  = "{\"propertyMetaData\":[{\"definition\":[\"href\",\"isCore\",\"name\",\"type\"],\"name\":\"favoriteArtists\"," +
                "\"navigationLinks\":{},\"isCore\":false,\"type\":\"Collection<Artist>\",\"href\":\"/metadata/artist.json\"}," +
                "{\"definition\":[\"href\",\"isCore\",\"name\",\"type\"],\"name\":\"friends\",\"navigationLinks\":{}," +
                "\"isCore\":false,\"type\":\"Collection<User>\",\"href\":\"/metadata/user.json\"}," +
                "{\"definition\":[\"href\",\"isCore\",\"name\",\"type\"],\"name\":\"id\",\"navigationLinks\":{},\"isCore\":true,\"type\":\"long\"}," +
                "{\"definition\":[\"href\",\"isCore\",\"name\",\"type\"],\"name\":\"isFriend\",\"navigationLinks\":{}," +
                "\"isCore\":false,\"type\":\"boolean\"},{\"definition\":[\"href\",\"isCore\",\"name\",\"type\"]," +
                "\"name\":\"name\",\"navigationLinks\":{},\"isCore\":true,\"type\":\"String\"}," +
                "{\"definition\":[\"href\",\"isCore\",\"name\",\"type\"],\"name\":\"recommendedAlbums\",\"navigationLinks\":{}," +
                "\"isCore\":false,\"type\":\"Collection<Album>\",\"href\":\"/metadata/album.json\"}]," +
                "\"definition\":[\"name\",\"propertyMetaData\"],\"name\":\"User\",\"navigationLinks\":{}}";
        
        JSONObject data = getJSONObject( "/metadata/user", null );
        JSONAssert.assertEquals( expected, data, false );
    }
}
