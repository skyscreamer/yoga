package org.skyscreamer.yoga.demo.test.resteasy;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA. User: cpage Date: 9/15/11 Time: 6:48 PM To change
 * this template use File | Settings | File Templates.
 */
public class ArtistControllerTest extends org.skyscreamer.yoga.demo.test.ArtistControllerTest {
	@BeforeClass
	public static void startServer() throws Exception {
		RunServer.startServer();
	}

	@Test
	public void testGetUser() throws Exception {
		super.testGetUser();
	}

	@Test
	public void testGetFans() throws Exception {
		super.testGetFans();
	}

	@Test
	public void getAlbums() throws Exception {
		super.getAlbums();
	}
}
