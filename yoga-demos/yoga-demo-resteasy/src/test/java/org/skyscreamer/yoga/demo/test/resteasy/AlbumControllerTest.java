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
public class AlbumControllerTest extends org.skyscreamer.yoga.demo.test.AlbumControllerTest {
    @BeforeClass
    public static void startServer() throws Exception {
        RunServer.startServer();
    }

    @Test
    public void testGetAlbum() throws Exception {
        super.testGetAlbum();
    }

    @Test
    public void testGetArtist() throws Exception {
        super.testGetArtist();
    }

    @Test
    public void testGetSongs() throws Exception {
        super.testGetSongs();
    }
}
