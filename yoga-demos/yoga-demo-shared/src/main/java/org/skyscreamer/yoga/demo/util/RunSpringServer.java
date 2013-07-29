package org.skyscreamer.yoga.demo.util;

import javax.servlet.ServletContext;

import org.skyscreamer.yoga.demo.test.SpringBeanContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RunSpringServer extends RunServer
{

    public RunSpringServer()
    {
        super();
    }

    public RunSpringServer( int port )
    {
        super(port);
    }

    @Override
    protected void init()
    {
        ServletContext servletContext = context.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        TestUtil.setContext(new SpringBeanContext(webApplicationContext));
    }
}
