package org.skyscreamer.yoga.demo.test.resteasy;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cpage
 * Date: 9/15/11
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongControllerTest extends org.skyscreamer.yoga.demo.test.SongControllerTest {
    @BeforeClass
    public static void startServer() throws Exception {
        RunServer.startServer();
    }

    @Test
    public void testGetSong() throws Exception {
        super.testGetSong();
    }

    @Test
    public void testGetArtistAndAlbum() throws Exception {
        super.testGetArtistAndAlbum();
    }
}
