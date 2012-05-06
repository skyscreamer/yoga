package org.skyscreamer.yoga.mapper;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class YogaRequestContext
{
    private final String urlSuffix;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();

    public YogaRequestContext( String urlSuffix, HttpServletResponse response )
    {
        this.urlSuffix = urlSuffix;
        this.response = response;
    }

    public String getUrlSuffix()
    {
        return urlSuffix;
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
}
