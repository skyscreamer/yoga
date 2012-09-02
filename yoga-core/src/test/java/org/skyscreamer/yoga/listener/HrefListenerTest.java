package org.skyscreamer.yoga.listener;

import junit.framework.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.AlbumFieldPopulator;
import org.skyscreamer.yoga.test.model.extended.User;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

import java.util.Map;

/**
 * User: corby Date: 5/14/12
 */
public class HrefListenerTest extends AbstractTraverserTest
{
    // Put an HrefListener in the listener chain. Traverse a user object where
    // the @URITemplate is defined
    // as "/user/{id}". Verify that the correct URL is appended to the output.
    @Test
    public void testAnnotatedModel()
    {
        User solomon = DataGenerator.solomon();
        ResultTraverser traverser = new ResultTraverser();

        YogaRequestContext requestContext = new YogaRequestContext( "test", new GDataSelectorParser(),
                new DummyHttpServletRequest(), new DummyHttpServletResponse(), new HrefListener() );
        Map<String, Object> objectTree = doTraverse( solomon, ":", traverser, requestContext );

        Assert.assertEquals( "/user/" + solomon.getId() + ".test", objectTree.get( "href" ) );
    }

    // No @URITemplate annotation exists on the Album class, but the
    // AlbumFieldPopulator defines a template.
    // Put an HrefListener in the listener chain, and verify that the correct
    // URL is appended to the output
    @Test
    public void testFieldPopulator()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = new ResultTraverser();
        populatorRegistry.register( new AlbumFieldPopulator() );

        YogaRequestContext requestContext = new YogaRequestContext( "test", new GDataSelectorParser(),
                new DummyHttpServletRequest(), new DummyHttpServletResponse(), new HrefListener( this.populatorRegistry ) );
        Map<String, Object> objectTree = doTraverse( funeral, ":", traverser, requestContext );

        Assert.assertEquals( "/album/" + funeral.getId() + ".test", objectTree.get( "href" ) );
    }
}
