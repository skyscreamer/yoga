package org.skyscreamer.yoga.populator;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.test.DummyHttpServletResponse;
import org.skyscreamer.yoga.uri.URICreator;

public class AbstractFieldPopulatorHrefTest
{

    @Test
    public void testSimple()
    {
        String href = new URICreator().getHref( "/foo/{id}", new DummyHttpServletResponse(),
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
        String href = new URICreator().getHref( "/{a}/foo/{b}/{c}", new DummyHttpServletResponse(),
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
