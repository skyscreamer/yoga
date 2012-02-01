package org.skyscreamer.yoga.demo.test.resteasy;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cpage
 * Date: 9/15/11
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserControllerTest extends org.skyscreamer.yoga.demo.test.UserControllerTest {
    @BeforeClass
    public static void startServer() throws Exception {
        RunServer.startServer();
    }

    @Test
    public void testGetUser() throws Exception {
        super.testGetUser();
    }

    @Test
    public void testGetNonExistentUser() throws Exception {
        super.testGetNonExistentUser();
    }

    @Test
    public void testGetUsers() throws Exception {
        super.testGetUsers();
    }

    @Test
    public void testGetUserWithSelector() throws Exception {
        super.testGetUserWithSelector();
    }

    @Test
    public void testGetUserWithFriends() throws Exception {
        super.testGetUserWithFriends();
    }

    @Test
    public void testDeepDiveSelector() throws Exception {
        super.testDeepDiveSelector();
    }

    @Test
    public void testRecommendedAlbums() throws Exception {
        super.testRecommendedAlbums();
    }
}
