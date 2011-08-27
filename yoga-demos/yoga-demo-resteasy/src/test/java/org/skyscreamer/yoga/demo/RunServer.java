package org.skyscreamer.yoga.demo;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;


public class RunServer {

    public static RunServer instance;

    Server server;

    public RunServer() {
        this(8082);
    }

    public RunServer(int port) {
        server = new Server(port);
    }

    public static void main(String[] args) throws Exception {
        RunServer runServer = new RunServer(8082);
        runServer.run();
    }

    public void run() throws Exception {
        run(true);
    }

    public void run(boolean join) throws Exception {
        WebAppContext context = new WebAppContext();

        context.setResourceBase("src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        server.start();
        if (join) {
            server.join();
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

} 