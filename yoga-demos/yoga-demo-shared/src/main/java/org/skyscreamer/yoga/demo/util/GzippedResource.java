package org.skyscreamer.yoga.demo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

import org.springframework.core.io.UrlResource;

/**
 * Wraps a gzipped Resource so that it decompresses on the fly.
 *
 * @author Carter Page
 */
public class GzippedResource extends UrlResource
{

    public GzippedResource( String path ) throws MalformedURLException
    {
        super( path );
    }

    public GzippedResource( URI uri ) throws MalformedURLException
    {
        super( uri );
    }

    public GzippedResource( URL url )
    {
        super( url );
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        try
        {
            return new GZIPInputStream( super.getInputStream() );
        }
        catch (UnknownHostException e)
        {
            return new ByteArrayInputStream( new byte[0] );
        }
    }
}
