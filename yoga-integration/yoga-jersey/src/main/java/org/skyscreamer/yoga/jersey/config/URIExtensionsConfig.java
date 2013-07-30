package org.skyscreamer.yoga.jersey.config;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.PackagesResourceConfig;

/**
 * make Jersey compatible with .json, .xhtml and .xml extensions as per
 * http://zcox.wordpress.com/2009/08/11/uri-extensions-in-jersey/
 * @author solomon.duskis
 *
 */
public class URIExtensionsConfig extends PackagesResourceConfig
{


    public URIExtensionsConfig()
    {
        super();
    }

    public URIExtensionsConfig(Map<String, Object> props)
    {
        super(props);
    }

    public URIExtensionsConfig(String[] paths)
    {
        super(paths);
    }

    @Override
    public Map<String, MediaType> getMediaTypeMappings()
    {
        return YogaMediaTypes.getMediaTypeMappings();
    }
}
