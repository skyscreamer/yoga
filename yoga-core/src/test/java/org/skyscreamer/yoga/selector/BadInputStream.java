package org.skyscreamer.yoga.selector;

import java.io.IOException;
import java.io.InputStream;

public class BadInputStream extends InputStream
{

    @Override
    public int read() throws IOException
    {
        throw new IOException("We want this to fail");
    }

}
