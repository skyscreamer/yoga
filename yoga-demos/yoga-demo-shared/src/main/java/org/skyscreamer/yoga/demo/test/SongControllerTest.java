package org.skyscreamer.yoga.demo.test;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Collections;
import java.util.Map;

import static org.skyscreamer.yoga.demo.util.TestUtil.getJSONObject;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/19/11 Time: 6:17 PM
 */
public class SongControllerTest
{
    @Test
    public void testGetSong() throws Exception
    {
        JSONObject data = getJSONObject( "/song/2", null );
        String expected = "{id:2,title:\"Wake Up\"," + "navigationLinks:{artist:{name:\"artist\",href:\"/song/2.json?selector=artist\"},"
                + "album:{name:\"album\",href:\"/song/2.json?selector=album\"}}}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testGetArtistAndAlbum() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "artist,album" );
        JSONObject data = getJSONObject( "/song/4", params );
        String expected = "{artist:{name:\"Arcade Fire\"},album:{title:\"Neon Bible\"}}";
        JSONAssert.assertEquals( expected, data, false );

        data = getJSONObject( "/song/14", params );
        expected = "{artist:{name:\"Prince\"},album:{title:\"Purple Rain\"}}";
        JSONAssert.assertEquals( expected, data, false );
    }
}
