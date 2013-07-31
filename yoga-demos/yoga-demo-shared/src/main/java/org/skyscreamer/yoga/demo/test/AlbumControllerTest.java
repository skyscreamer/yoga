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
public class AlbumControllerTest
{

    @Test
    public void testGetAlbum() throws Exception
    {
        JSONObject data = getJSONObject( "/album/1", null );
        String expected = "{id:1,title:\"Funeral\",year:\"2004\","
                + "navigationLinks:{artist:{name:\"artist\",href:\"/album/1.json?selector=artist\"},"
                + "songs:{name:\"songs\",href:\"/album/1.json?selector=songs\"}}}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testGetArtist() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "artist" );
        JSONObject data = getJSONObject( "/album/1", params );
        String expected = "{artist:{name:\"Arcade Fire\"}}";
        JSONAssert.assertEquals( expected, data, false );

        data = getJSONObject( "/album/5", params );
        expected = "{artist:{name:\"Prince\"}}";
        JSONAssert.assertEquals( expected, data, false );

        data = getJSONObject( "/album/8", params );
        expected = "{artist:{name:\"Neutral Milk Hotel\"}}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testGetSongs() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "songs" );
        JSONObject data = getJSONObject( "/album/1", params );
        String expected = "{songs:[{title:\"Neighborhood #1 (Tunnels)\"},{title:\"Wake Up\"},{title:\"Haiti\"}]}";
        JSONAssert.assertEquals( expected, data, false );
    }
}
