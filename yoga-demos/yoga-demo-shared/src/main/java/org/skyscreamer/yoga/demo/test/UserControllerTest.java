package org.skyscreamer.yoga.demo.test;

import java.util.Collections;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.yoga.util.EntityCountExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
public class UserControllerTest extends AbstractTest {
    public void testGetUser() throws Exception {
        JSONObject data = getJSONObject("/user/2", null);
        String expected = "{id:2,name:\"Corby Page\"," +
                "navigationLinks:{friends:{name:\"friends\",href:\"/user/friends/2.json\"}," +
                "favoriteArtists:{name:\"favoriteArtists\",href:\"/user/favoriteArtists/2.json\"}," +
                "isFriend:{name:\"isFriend\",href:\"/user/isFriend/2.json\"}}}";
        JSONAssert.assertEquals(expected, data, false);
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
        Assert.assertEquals(1003, data.length());
    }

    public void testGetUserWithSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(isFriend)");
        JSONObject data = getJSONObject("/user/1", params);
        String expected = "{id:1,name:\"Carter Page\",href:\"/user/1.json\",isFriend:false}";
        JSONAssert.assertEquals(expected, data, false);
    }

    public void testGetUserWithFriends() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends)");
        JSONObject data = getJSONObject("/user/1", params);
        String expected = "{id:1,name:\"Carter Page\",href:\"/user/1.json\"," +
                "friends:[{id:2,name:\"Corby Page\",href:\"/user/2.json\"}," +
                "{id:3,name:\"Solomon Duskis\",href:\"/user/3.json\"}]}";
        JSONAssert.assertEquals(expected, data, false);
    }

    public void testDeepDiveSelector() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(isFriend,friends:(favoriteArtists:(albums:(songs))))");
        JSONObject data = getJSONObject("/user/1", params);
        String expected = "{id:1,name:\"Carter Page\",href:\"/user/1.json\",isFriend:false," +
                "friends:[{id:2,name:\"Corby Page\",href:\"/user/2.json\"," +
                "favoriteArtists:[{id:2,name:\"Prince\",albums:[{id:4,title:\"1999\"," +
                "songs:[{id:10},{id:11,title:\"Little Red Corvette\"},{id:12}]},{id:5},{id:6}]},{id:1}]}," +
                "{id:3}]}";
        JSONAssert.assertEquals(expected, data, false);
    }

    public void testRecommendedAlbums() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(recommendedAlbums)");
        JSONObject data = getJSONObject("/user/1", params);
        JSONArray recommended = data.getJSONArray("recommendedAlbums");
        Assert.assertNotNull(recommended);
    }

    // This should retrieve a LOT of data and throw EntityCountExceededException
    public void testGetTooMuchData() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(friends:(favoriteArtists))");
        try {
            getJSONObject("/user", params);
        }
        catch (HttpServerErrorException e) {
            Assert.assertNotNull(e.getMessage());
            Assert.assertTrue(e.getMessage().contains("EntityCountExceededException"));
            return;
        }
        Assert.fail("Expected this query to fail with a 500 error caused by an EntityCountExceededException");
    }
}
