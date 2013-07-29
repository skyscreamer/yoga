package org.skyscreamer.yoga.demo.test.resteasy;

import org.skyscreamer.yoga.demo.util.RunSpringServer;

public class RunServer extends RunSpringServer
{

    public static RunSpringServer instance;

    public static void main( String[] args ) throws Exception
    {
        RunSpringServer runServer = new RunSpringServer( 8081 );
        runServer.run();
    }

    public static void startServer() throws Exception
    {
        if ( instance == null )
        {
            instance = new RunSpringServer( 8082 );
            instance.run( false );
        }
    }

}
