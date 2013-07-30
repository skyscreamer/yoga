package org.skyscreamer.yoga.demo.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;

public class StaticContentServletFilter implements Filter
{

    @Override
    public void destroy()
    {

    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response,
            FilterChain chain ) throws IOException, ServletException
    {
        String path = ((HttpServletRequest) request).getRequestURI();
        if (path.equals("/"))
        {
            path = "/index.html";
        }
        ServletOutputStream outputStream = null;
        InputStream resourceAsStream = null;
        try
        {
            ClassPathResource resource = new ClassPathResource("webapp" + path);
            if (resource.exists())
            {
                resourceAsStream = resource.getInputStream();
                outputStream = response.getOutputStream();
                byte buff[] = new byte[2 << 12];
                int read = 0;
                while ((read = resourceAsStream.read(buff)) > 0)
                {
                    outputStream.write(buff, 0, read);
                }
                outputStream.flush();
            }
            else
            {
                chain.doFilter(request, response);
            }
        }
        catch (Exception e)
        {

        }
        finally
        {
            if (outputStream != null)
                outputStream.close();
            if (resourceAsStream != null)
                resourceAsStream.close();
        }
    }

    @Override
    public void init( FilterConfig config ) throws ServletException
    {
    }

}
