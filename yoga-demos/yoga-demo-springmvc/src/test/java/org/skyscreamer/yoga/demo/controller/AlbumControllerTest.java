package org.skyscreamer.yoga.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:17 PM
 */
public class AlbumControllerTest extends AbstractTest {

    @Test
    public void testGetAlbum() throws Exception {
        JSONObject data = getJSONObject( "/album/1", null );
        Assert.assertEquals("Funeral", data.getString("title"));
        Assert.assertEquals(1, data.getLong("id"));
        Assert.assertEquals(2004, data.getInt("year"));
        Assert.assertEquals(4, data.length());
    }

    @Test
    public void testGetArtist() throws Exception {
    	Map<String, String> params = Collections.singletonMap("selector", ":(artist)");
        JSONObject data = getJSONObject( "/album/1", params );
        Assert.assertEquals("Arcade Fire", data.getJSONObject("artist").getString("name"));

        data = getJSONObject( "/album/5", params );
        Assert.assertEquals("Prince", data.getJSONObject("artist").getString("name"));

        data = getJSONObject( "/album/8", params );
        Assert.assertEquals("Neutral Milk Hotel", data.getJSONObject("artist").getString("name"));
    }

    @Test
    public void testGetSongs() throws Exception {
    	Map<String, String> params = Collections.singletonMap("selector", ":(songs)");
        JSONObject data = getJSONObject( "/album/1", params );
        Assert.assertEquals("Haiti", data.getJSONArray("songs").getJSONObject(2).getString("title"));

        data = getJSONObject( "/album/5", params );
        Assert.assertEquals("When Doves Cry", data.getJSONArray("songs").getJSONObject(1).getString("title"));

        data = getJSONObject( "/album/8", params );
        Assert.assertEquals("Two-Headed Boy", data.getJSONArray("songs").getJSONObject(1).getString("title"));
    }
}
