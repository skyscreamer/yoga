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
public class SongControllerTest extends AbstractTest {
    @Test
    public void testGetSong() throws Exception {
        JSONObject data = getJSONObject( "/song/2", null );
        Assert.assertEquals("Wake Up", data.getString("title"));
        Assert.assertEquals(2, data.getLong("id"));
        Assert.assertEquals(2, data.length());
    }

    @Test
    public void testGetArtistAndAlbum() throws Exception {
        Map<String, String> params = Collections.singletonMap("selector", ":(artist,album)");
        JSONObject data = getJSONObject( "/song/4", params );
        Assert.assertEquals("Arcade Fire", data.getJSONObject("artist").getString("name"));
        Assert.assertEquals("Neon Bible", data.getJSONObject("album").getString("title"));

        data = getJSONObject( "/song/14", params );
        Assert.assertEquals("Prince", data.getJSONObject("artist").getString("name"));
        Assert.assertEquals("Purple Rain", data.getJSONObject("album").getString("title"));

        data = getJSONObject( "/song/20", params );
        Assert.assertEquals("Neutral Milk Hotel", data.getJSONObject("artist").getString("name"));
        Assert.assertEquals("On Avery Island", data.getJSONObject("album").getString("title"));
    }
}
