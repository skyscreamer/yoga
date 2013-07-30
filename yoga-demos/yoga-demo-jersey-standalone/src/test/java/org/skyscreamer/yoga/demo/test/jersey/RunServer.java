package org.skyscreamer.yoga.demo.test.jersey;

import org.skyscreamer.yoga.demo.util.JettyServer;

public class RunServer extends JettyServer
{
    private static JettyServer instance;

    public static void main( String[] args ) throws Exception
    {
        createServer( 8081, true );
    }

    public static void startServer() throws Exception
    {
        if ( instance == null )
        {
            instance = createServer( 8082, false );
        }
    }

}
