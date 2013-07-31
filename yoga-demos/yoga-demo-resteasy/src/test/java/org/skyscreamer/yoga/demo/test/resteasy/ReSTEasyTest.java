package org.skyscreamer.yoga.demo.test.resteasy;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.skyscreamer.yoga.demo.test.AlbumControllerTest;
import org.skyscreamer.yoga.demo.test.ArtistControllerTest;
import org.skyscreamer.yoga.demo.test.SongControllerTest;
import org.skyscreamer.yoga.demo.test.UserControllerTest;
import org.skyscreamer.yoga.demo.util.JettyServer;
import org.skyscreamer.yoga.demo.util.RunSpringServer;

@RunWith(Suite.class)
@SuiteClasses({ AlbumControllerTest.class, ArtistControllerTest.class, SongControllerTest.class,
        UserControllerTest.class })
public class ReSTEasyTest
{
    public static JettyServer instance;

    @BeforeClass
    public static void startServer() throws Exception
    {
        instance = RunSpringServer.initServer( 8082, false );
    }

    @AfterClass
    public static void endServer() throws Exception
    {
        instance.stop();
    }
}
