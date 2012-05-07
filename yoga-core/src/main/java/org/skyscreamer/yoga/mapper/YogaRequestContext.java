package org.skyscreamer.yoga.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YogaRequestContext
{
    private final String urlSuffix;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> properties = new HashMap<String, Object>();

    public YogaRequestContext(String urlSuffix, HttpServletRequest request, HttpServletResponse response)
    {
        this.urlSuffix = urlSuffix;
        this.request = request;
        this.response = response;
    }

    public String getUrlSuffix()
    {
        return urlSuffix;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setProperty(String key, Object value)
    {
        properties.put( key, value );
    }

    public Object getProperty(String key)
    {
        return properties.get( key );
    }
}
