package org.skyscreamer.yoga.demo.util;

import java.util.Collections;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyServer
{

    public static JettyServer createServer( int port, boolean join ) throws Exception
    {
        JettyServer server = new JettyServer( port );
        server.run( join );
        return server;
    }

    Server server;
    WebAppContext context;

    public JettyServer()
    {
        this( 8082 );
    }

    public JettyServer( int port )
    {
        System.setProperty( "org.mortbay.util.FileResource.checkAliases", "false" );
        server = new Server( port );
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
