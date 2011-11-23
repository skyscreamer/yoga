package org.skyscreamer.yoga.demo.test;

import java.util.Collections;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
public class UserControllerTest extends AbstractTest {
    public void testGetUser() throws Exception {
        JSONObject data = getJSONObject("/user/2", null);
        Assert.assertEquals("Corby Page", data.getString("name"));
//        Assert.assertEquals(4, data.length());
        Assert.assertEquals(2, data.getLong("id"));
        Assert.assertEquals("/user/2.json", data.getString("href"));
        testForNavigationLinks(data, "/user/2.json", "friends", "favoriteArtists", "isFriend");
    }

    public void testGetNonExistentUser() throws Exception {
        try {
            getJSONObject("/user/8675309", null);
            Assert.fail("HttpServerErrorException expected.");
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    public void testGetUsers() throws Exception {
        JSONArray data = getJSONArray("/user", null);
//        Assert.assertEquals(3, data.length());
        JSONObject carter = data.getJSONObject(0);
//        Assert.assertEquals(4, carter.length());
        Assert.assertEquals("Carter Page", carter.getString("name"));
        Assert.assertEquals(1, carter.getLong("id"));
        Assert.assertEquals("/user/1.json", carter.getString("href"));
        testForNavigationLinks(carter, "/user/1.json", "friends", "favoriteArtists", "isFriend");

        JSONObject corby = data.getJSONObject(1);
        Assert.assertEquals("Corby Page", corby.getString("name"));
//        Assert.assertEquals(4, corby.length());
        Assert.assertEquals(2, corby.getLong("id"));
        Assert.assertEquals("/user/2.json", corby.getString("href"));
        testForNavigationLinks(corby, "/user/2.json", "friends", "favoriteArtists", "isFriend");
    }

    public void testGetUserWithSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(isFriend)");
        JSONObject data = getJSONObject("/user/1", params);
        checkCarter(data);
        Assert.assertFalse(data.getBoolean("isFriend"));
    }

    public void testGetUserWithFriends() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends)");
        JSONObject data = getJSONObject("/user/1", params);
        checkCarter(data);
        JSONArray friends = data.getJSONArray("friends");
//        Assert.assertEquals(2, friends.length());
        JSONObject friend = (friends.getJSONObject(0).getString("name").equals("Corby Page"))
                ? friends.getJSONObject(0) : friends.getJSONObject(1);
//        Assert.assertEquals(3, friend.length());
        Assert.assertEquals("Corby Page", friend.getString("name"));
        Assert.assertEquals("/user/2.json", friend.getString("href"));
    }

    public void testGetUserWithFriendsAndSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends:(isFriend))");
        JSONObject data = getJSONObject("/user/1", params);
        checkCarter(data);
        JSONArray friends = data.getJSONArray("friends");
//        Assert.assertEquals(2, friends.length());
        JSONObject friend = (friends.getJSONObject(0).getString("name").equals("Corby Page"))
                ? friends.getJSONObject(0) : friends.getJSONObject(1);
        checkCorby(friend);
        Assert.assertFalse(friend.getBoolean("isFriend"));
    }

    public void testDeepDiveSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends:(favoriteArtists:(albums:(songs))))");
        JSONObject data = getJSONObject("/user/1", params);
        checkCarter(data);
        JSONArray friends = data.getJSONArray("friends");
//        Assert.assertEquals(2, friends.length());
        JSONObject friend = (friends.getJSONObject(0).getString("name").equals("Corby Page"))
                ? friends.getJSONObject(0) : friends.getJSONObject(1);
        checkCorby(friend);
        JSONArray favoriteArtists = friend.getJSONArray("favoriteArtists");
//        Assert.assertEquals(2, favoriteArtists.length());
        JSONObject prince = "Prince".equals(favoriteArtists.getJSONObject(0).getString("name"))
                ? favoriteArtists.getJSONObject(0) : favoriteArtists.getJSONObject(1);
        JSONArray albums = prince.getJSONArray("albums");
        JSONObject prince1999 = albums.getJSONObject(0);
        JSONObject corvette = prince1999.getJSONArray("songs").getJSONObject(1);
        Assert.assertEquals("Little Red Corvette", corvette.getString("title"));
    }

    private void checkCorby(JSONObject data) throws JSONException {
//        Assert.assertEquals(4, data.length());
        Assert.assertEquals("Corby Page", data.getString("name"));
//        Assert.assertEquals(2, data.getLong("id"));
        Assert.assertEquals("/user/2.json", data.getString("href"));
    }

    private void checkCarter(JSONObject data) throws JSONException {
//        Assert.assertEquals(4, data.length());
        Assert.assertEquals("Carter Page", data.getString("name"));
//        Assert.assertEquals(1, data.getLong("id"));
        Assert.assertEquals("/user/1.json", data.getString("href"));
    }

    public void testRecommendedAlbums() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(recommendedAlbums)");
        JSONObject data = getJSONObject("/user/1", params);
        JSONArray recommended = data.getJSONArray("recommendedAlbums");
        Assert.assertNotNull(recommended);
    }

}
