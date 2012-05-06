package org.skyscreamer.yoga.selector;

import org.junit.Test;

/**
 * Tests for GDataSelector
 *
 * @author Carter Page <carter@skyscreamer.org>
 */
public class GDataSelectorTest extends AbstractSelectorTest
{
    private SelectorParser _selectorParser = new GDataSelectorParser();

    @Test
    public void testSimpleSelector() throws Exception
    {
        Selector selector = _selectorParser.parseSelector( "gender,country" );
        testSimpleSelector( selector );
    }

    @Test
    public void testNestedSelectors() throws Exception
    {
        Selector selector = _selectorParser.parseSelector( "gender,favoriteArtists(birthday,discography(year,title)),friends" );
        testNestedSelectors( selector );
    }
}
