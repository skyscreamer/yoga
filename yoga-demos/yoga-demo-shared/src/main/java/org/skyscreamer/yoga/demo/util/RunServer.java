package org.skyscreamer.yoga.demo.util;

import java.util.Collections;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class RunServer
{

    public static RunServer instance;

    Server server;
    WebAppContext context;

    public RunServer()
    {
        this( 8082 );
    }

    public RunServer( int port )
    {
        System.setProperty( "org.mortbay.util.FileResource.checkAliases", "false" );
        server = new Server( port );
    }

    public static void main( String[] args ) throws Exception
    {
        RunServer runServer = new RunServer( 8081 );
        runServer.run();
    }

    public static void startServer() throws Exception
    {
        if ( instance == null )
        {
            instance = new RunServer( 8082 );
            instance.run( false );
        }
    }

    public void run() throws Exception
    {
        run( true );
    }

    public void run( boolean join ) throws Exception
    {
        context = new WebAppContext();

        context.setResourceBase( "src/main/webapp" );
        context.setContextPath( "/" );
        context.setParentLoaderPriority( true );
        context.setInitParams( Collections.singletonMap( "org.mortbay.jetty.servlet.Default.aliases", "true" ) );

        server.setHandler( context );

        server.start();

        init();

        if ( join )
        {
            server.join();
        }
    }

    protected void init()
    {
        // a child can do something interesting here.
    }

    public void stop() throws Exception
    {
        server.stop();
    }

}
