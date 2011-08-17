package org.skyscreamer.yoga.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
public class UserControllerTest extends AbstractTest {
    @Test
    public void testGetUser() throws Exception {
        JSONObject data = getJSONObject( "/user/2", null );
        Assert.assertEquals("Corby Page", data.getString("name"));
        Assert.assertEquals(3, data.length());
        Assert.assertEquals(2, data.getLong( "id" ));
        Assert.assertEquals( "/user/2.json", data.getString( "href" ) );
    }

    @Test
    public void testGetNonExistantUser() throws Exception {
    	try {
    		getJSONObject( "/user/8675309", null );
    		Assert.fail("HttpServerErrorException expected.");
    	}
    	catch (HttpClientErrorException e) {
    		Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    	}
    }

    @Test
    public void testGetUsers() throws Exception {
        JSONArray data = getJSONArray( "/user", null );
        Assert.assertEquals(2, data.length());
        JSONObject carter = data.getJSONObject( 0 );
        Assert.assertEquals(3, carter.length());
        Assert.assertEquals("Carter Page", carter.getString( "name" ));
        Assert.assertEquals(1, carter.getLong( "id" ));
        Assert.assertEquals( "/user/1.json", carter.getString( "href" ) );
        JSONObject corby = data.getJSONObject( 1 );
        Assert.assertEquals("Corby Page", corby.getString("name"));
        Assert.assertEquals(3, corby.length());
        Assert.assertEquals(2, corby.getLong( "id" ));
        Assert.assertEquals( "/user/2.json", corby.getString( "href" ) );
    }

    @Test
    public void testGetUserWithSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(isFriend)");
        JSONObject data = getJSONObject( "/user/1", params );
        checkCarter( data );
        Assert.assertFalse(data.getBoolean("isFriend"));
    }

    @Test
    public void testGetUserWithFriends() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends)");
        JSONObject data = getJSONObject( "/user/1", params );
        checkCarter( data );
        JSONArray friends = data.getJSONArray("friends");
        Assert.assertEquals(1, friends.length());
        JSONObject friend = friends.getJSONObject(0);
        Assert.assertEquals(3, friend.length());
        Assert.assertEquals("Corby Page", friend.getString( "name" ));
        Assert.assertEquals( "/user/2.json", friend.getString( "href" ) );
    }

    @Test
    public void testGetUserWithFriendsAndSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends:(isFriend))");
        JSONObject data = getJSONObject( "/user/1", params );
        checkCarter( data );
        JSONArray friends = data.getJSONArray("friends");
        Assert.assertEquals(1, friends.length());
        JSONObject friend = friends.getJSONObject(0);
        checkCorby( friend );
        Assert.assertFalse(friend.getBoolean("isFriend"));
    }

    @Test
    public void testDeepDiveSelector() throws Exception
    {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends:(favoriteArtists:(albums:(songs))))");
        JSONObject data = getJSONObject( "/user/1", params );
        checkCarter( data );
        JSONArray friends = data.getJSONArray("friends");
        Assert.assertEquals(1, friends.length());
        JSONObject friend = friends.getJSONObject(0);
        checkCorby( friend );
        JSONArray favoriteArtists = friend.getJSONArray("favoriteArtists");
        Assert.assertEquals(2, favoriteArtists.length());
        JSONObject prince = "Prince".equals(favoriteArtists.getJSONObject(0).getString("name"))
            ? favoriteArtists.getJSONObject(0) : favoriteArtists.getJSONObject(1);
        JSONArray albums = prince.getJSONArray("albums");
        JSONObject prince1999 = albums.getJSONObject(0);
        JSONObject corvette = prince1999.getJSONArray("songs").getJSONObject(1);
        Assert.assertEquals("Little Red Corvette", corvette.getString("title"));
    }

    private void checkCorby( JSONObject friend ) throws JSONException
    {
        Assert.assertEquals( 4, friend.length() );
        Assert.assertEquals("Corby Page", friend.getString( "name" ));
        Assert.assertEquals(2, friend.getLong( "id" ));
        Assert.assertEquals( "/user/2.json", friend.getString( "href" ) );
    }

    private void checkCarter( JSONObject data ) throws JSONException
    {
        Assert.assertEquals( 4, data.length() );
        Assert.assertEquals( "Carter Page", data.getString( "name" ) );
        Assert.assertEquals( 1, data.getLong( "id" ) );
        Assert.assertEquals( "/user/1.json", data.getString( "href" ) );
    }
    
    @Test
    public void testRecommendedAlbums() throws Exception
    {
        Map<String, String> params = Collections.singletonMap("selector", ":(recommendedAlbums)");
        JSONObject data = getJSONObject("/user/1", params);
        JSONArray recommended = data.getJSONArray("recommendedAlbums");
		Assert.assertNotNull(recommended);
    }

}
