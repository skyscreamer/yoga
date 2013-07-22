package org.skyscreamer.yoga.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA. User: cpage Date: 12/10/11 Time: 4:58 PM To change
 * this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class StaticForwardServlet extends HttpServlet
{
    String path;

    @Override
    public void init( ServletConfig config ) throws ServletException
    {
        path = config.getInitParameter( "path" );
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException,
            IOException
    {
        req.getSession().getServletContext().getRequestDispatcher( path ).forward( req, resp );
    }
}
