package org.skyscreamer.yoga.demo.test.resteasy;

import java.io.File;
import java.util.Collections;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class RunServer
{

    public static RunServer instance;

    Server server;

    public RunServer()
    {
        this( 8082 );
    }

    public RunServer(int port)
    {
        System.setProperty( "org.mortbay.util.FileResource.checkAliases", "false" );
        server = new Server( port );
    }

    public static void main(String[] args) throws Exception
    {
        RunServer runServer = new RunServer( 8081 );
        runServer.run();
    }

    public static void startServer() throws Exception
    {
        if (instance == null)
        {
            instance = new RunServer( 8082 );
            instance.run( false );
        }
    }

    public void run() throws Exception
    {
        run( true );
    }

    public void run(boolean join) throws Exception
    {
        System.out.println( new File( "logs" ).getAbsolutePath() );
        WebAppContext context = new WebAppContext();

        context.setResourceBase( "src/main/webapp" );
        context.setContextPath( "/" );
        context.setParentLoaderPriority( true );
        context.setInitParams( Collections.singletonMap(
                "org.mortbay.jetty.servlet.Default.aliases", "true" ) );

        server.setHandler( context );

        server.start();
        if (join)
        {
            server.join();
        }
    }

    public void stop() throws Exception
    {
        server.stop();
    }

}
