package org.skyscreamer.yoga.util;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
<<<<<<< HEAD
 * Wraps a gzipped Resource so that it decompresses on the fly.
 * 
=======
 * Wraps a gzipped Resource for inline decompression.
 *
>>>>>>> upstream/master
 * @author Carter Page
 */
public class GzippedResource implements Resource
{
    private final Resource _resource;

    public GzippedResource(Resource resource)
    {
        _resource = resource;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return new GZIPInputStream( _resource.getInputStream() );
    }

    // Delegate Methods
    @Override
    public boolean exists()
    {
        return _resource.exists();
    }

    @Override
    public boolean isReadable()
    {
        return _resource.isReadable();
    }

    @Override
    public boolean isOpen()
    {
        return _resource.isOpen();
    }

    @Override
    public URL getURL() throws IOException
    {
        return _resource.getURL();
    }

    @Override
    public URI getURI() throws IOException
    {
        return _resource.getURI();
    }

    @Override
    public File getFile() throws IOException
    {
        return _resource.getFile();
    }

    @Override
    public long contentLength() throws IOException
    {
        return _resource.contentLength();
    }

    @Override
    public long lastModified() throws IOException
    {
        return _resource.lastModified();
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException
    {
        return _resource.createRelative( relativePath );
    }

    @Override
    public String getFilename()
    {
        return _resource.getFilename();
    }

    @Override
    public String getDescription()
    {
        return _resource.getDescription();
    }
}
