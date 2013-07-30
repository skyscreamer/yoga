package org.skyscreamer.yoga.demo.test.jersey;

import org.skyscreamer.yoga.demo.util.JettyServer;
import org.skyscreamer.yoga.demo.util.RunSpringServer;

public class RunServer extends RunSpringServer
{

    public static JettyServer instance;

    public static void main( String[] args ) throws Exception
    {
        RunSpringServer.initServer( 8081, true );
    }

    public static void startServer() throws Exception
    {
        if (instance == null)
        {
            instance = RunSpringServer.initServer( 8082, false );
        }
    }


}
