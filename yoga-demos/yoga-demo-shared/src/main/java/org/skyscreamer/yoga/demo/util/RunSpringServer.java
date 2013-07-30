package org.skyscreamer.yoga.demo.util;

import javax.servlet.ServletContext;

import org.skyscreamer.yoga.demo.test.SpringBeanContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RunSpringServer
{

    public static JettyServer initServer( int port, boolean join) throws Exception
    {
        JettyServer server = JettyServer.createServer( port, join );
        initSpring( server );
        return server;
    }

    public static void initSpring( JettyServer server )
    {
        ServletContext servletContext = server.context.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        TestUtil.setContext(new SpringBeanContext(webApplicationContext));
    }
}
