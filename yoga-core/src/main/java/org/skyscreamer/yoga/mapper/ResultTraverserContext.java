package org.skyscreamer.yoga.mapper;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ResultTraverserContext
{
    private final String hrefSuffix;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private int counter = 0;

    public ResultTraverserContext( String hrefSuffix, HttpServletResponse response )
    {
        this.hrefSuffix = hrefSuffix;
        this.response = response;
    }

    public String getHrefSuffix()
    {
        return hrefSuffix;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setProperty( String key, Object value )
    {
        properties.put( key, value );
    }

    public Object getProperty( String key )
    {
        return properties.get( key );
    }

    public void incrementCounter()
    {
        ++counter;
    }

    public int readCounter()
    {
        return counter;
    }
}
