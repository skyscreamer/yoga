package org.skyscreamer.yoga.selector;

import org.junit.Test;

/**
 * Test for LinkedInSelector
 *
 * @author Carter Page <carter@skyscreamer.org>
 */
public class LinkedInSelectorTest extends AbstractSelectorTest
{
    private SelectorParser _selectorParser = new LinkedInSelectorParser();

    @Test
    public void testSimpleSelector() throws Exception
    {
        Selector selector = _selectorParser.parseSelector( ":(gender,country)" );
        testSimpleSelector( selector );
    }

    @Test
    public void testNestedSelectors() throws Exception
    {
        Selector selector = _selectorParser.parseSelector( ":(gender,favoriteArtists:(birthday,discography:(year,title)),friends)" );
        testNestedSelectors( selector );
    }
}
