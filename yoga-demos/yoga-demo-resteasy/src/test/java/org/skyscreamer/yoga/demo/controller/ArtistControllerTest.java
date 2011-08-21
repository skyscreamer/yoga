package org.skyscreamer.yoga.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:16 PM
 */
public class ArtistControllerTest extends AbstractTest {
    @Test
    public void testGetUser() throws Exception {
        JSONObject data = getJSONObject("/artist/1", null);
        Assert.assertEquals("Arcade Fire", data.getString("name"));
        Assert.assertEquals(1, data.getLong("id"));
        Assert.assertEquals(3, data.length());
    }

    @Test
    public void testGetFans() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(fans)");
        JSONObject data = getJSONObject("/artist/1", params);
        JSONArray fans = data.getJSONArray("fans");
        Assert.assertEquals(2, fans.length());

        data = getJSONObject("/artist/2", params);
        fans = data.getJSONArray("fans");
        Assert.assertEquals(1, fans.length());
        Assert.assertEquals("Corby Page", fans.getJSONObject(0).getString("name"));

        data = getJSONObject("/artist/3", params);
        fans = data.getJSONArray("fans");
        Assert.assertEquals(1, fans.length());
        Assert.assertEquals("Carter Page", fans.getJSONObject(0).getString("name"));
    }

    @Test
    public void getAlbums() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(albums)");
        JSONObject data = getJSONObject("/artist/1", params);
        JSONArray albums = data.getJSONArray("albums");
        Assert.assertEquals(3, albums.length());
        Assert.assertEquals("The Suburbs", albums.getJSONObject(2).getString("title"));

        data = getJSONObject("/artist/2", params);
        albums = data.getJSONArray("albums");
        Assert.assertEquals(3, albums.length()); // I didn't feel like typing in all 4 dozen albums
        Assert.assertEquals("Purple Rain", albums.getJSONObject(1).getString("title"));

        data = getJSONObject("/artist/3", params);
        albums = data.getJSONArray("albums");
        Assert.assertEquals(2, albums.length());
        Assert.assertEquals("In the Aeroplane over the Sea", albums.getJSONObject(1).getString("title"));
    }
}
