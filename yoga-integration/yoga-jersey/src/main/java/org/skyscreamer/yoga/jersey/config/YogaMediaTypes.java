package org.skyscreamer.yoga.jersey.config;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

/**
 * make Jersey compatible with .json, .xhtml and .xml extensions as per
 * http://zcox.wordpress.com/2009/08/11/uri-extensions-in-jersey/
 * @author solomon.duskis
 *
 */
public class YogaMediaTypes 
{

    private static Map<String, MediaType> mediaTypeMap;

    static
    {
        mediaTypeMap = new HashMap<String, MediaType>();
        mediaTypeMap.put( "json", MediaType.APPLICATION_JSON_TYPE);
        mediaTypeMap.put( "xml", MediaType.APPLICATION_XML_TYPE );
        mediaTypeMap.put( "txt", MediaType.TEXT_PLAIN_TYPE );
        mediaTypeMap.put( "html", MediaType.TEXT_HTML_TYPE );
        mediaTypeMap.put( "xhtml", MediaType.APPLICATION_XHTML_XML_TYPE );

        MediaType jpeg = new MediaType("image", "jpeg");
        mediaTypeMap.put( "jpg", jpeg );
        mediaTypeMap.put( "jpeg", jpeg);
        mediaTypeMap.put( "zip", new MediaType( "application", "x-zip-compressed" ) );
        mediaTypeMap.put( "yoga", MediaType.TEXT_HTML_TYPE );
    }

    public static Map<String, MediaType> getMediaTypeMappings()
    {
        return mediaTypeMap;
    }
}
