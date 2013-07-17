package org.skyscreamer.yoga.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.uri.URICreator;
import org.skyscreamer.yoga.util.ValueReader;

public class AbstractEntityConfigurationHrefTest
{

    @Test
    public void testSimple()
    {
        String href = URICreator.getHref( "/foo/{id}",
                new ValueReader()
                {

                    @Override
                    public Object getValue( String property )
                    {
                        return "123";
                    }
                } );

        Assert.assertEquals( "/foo/123", href );
    }

    @Test
    public void test3()
    {
        String href = URICreator.getHref( "/{a}/foo/{b}/{c}",
                new ValueReader()
                {
                    @Override
                    public Object getValue( String property )
                    {
                        return property + "TT";
                    }
                } );

        Assert.assertEquals( "/aTT/foo/bTT/cTT", href );
    }

}
