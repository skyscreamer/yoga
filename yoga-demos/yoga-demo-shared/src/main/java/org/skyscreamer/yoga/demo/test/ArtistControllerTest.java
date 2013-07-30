package org.skyscreamer.yoga.demo.test;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Collections;
import java.util.Map;

import static org.skyscreamer.yoga.demo.util.TestUtil.getJSONObject;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/19/11 Time: 6:16 PM
 */
public class ArtistControllerTest
{
    @Test
    public void testGetUser() throws Exception
    {
        JSONObject data = getJSONObject( "/artist/1", null );
        String expected = "{id:1,name:\"Arcade Fire\"," + "navigationLinks:{albums:{name:\"albums\",href:\"/artist/1.json?selector=albums\"},"
                + "fans:{name:\"fans\",href:\"/artist/1.json?selector=fans\"}}}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void testGetFans() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "fans" );
        JSONObject data = getJSONObject( "/artist/1", params );
        String expected = "{fans:[{name:\"Corby Page\"},{name:\"Carter Page\"}]}";
        JSONAssert.assertEquals( expected, data, false );
    }

    @Test
    public void getAlbums() throws Exception
    {
        Map<String, String> params = Collections.singletonMap( "selector", "albums" );
        JSONObject data = getJSONObject( "/artist/1", params );
        String expected = "{albums:[{title:\"Funeral\"},{title:\"Neon Bible\"},{title:\"The Suburbs\"}]}";
        JSONAssert.assertEquals( expected, data, false );
    }
}
