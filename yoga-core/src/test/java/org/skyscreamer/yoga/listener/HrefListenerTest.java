package org.skyscreamer.yoga.listener;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.AlbumEntityConfiguration;
import org.skyscreamer.yoga.test.model.extended.User;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;

/**
 * User: corby Date: 5/14/12
 */
public class HrefListenerTest extends AbstractTraverserTest
{
    // Put an HrefListener in the listener chain. Traverse a user object where
    // the @URITemplate is defined as "/user/{id}". Verify that the correct URL
    // is appended to the output.
    @Test
    public void testAnnotatedModel()
    {
        User solomon = DataGenerator.solomon();
        ResultTraverser traverser = new ResultTraverser();

        Map<String, Object> objectTree = doTraverse( solomon, ":", traverser, new HrefListener() );

        Assert.assertEquals( "/user/" + solomon.getId() + ".test", objectTree.get( "href" ) );
    }

    // No @URITemplate annotation exists on the Album class, but the
    // AlbumEntityConfiguration defines a template.
    // Put an HrefListener in the listener chain, and verify that the correct
    // URL is appended to the output
    @Test
    public void testEntityConfiguration()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = new ResultTraverser();
        getEntityConfigurationRegistry().register( new AlbumEntityConfiguration() );

        HrefListener listener = new HrefListener( new UriGenerator( getEntityConfigurationRegistry() ) );
        Map<String, Object> objectTree = doTraverse( funeral, ":", traverser, listener );

        Assert.assertEquals( "/album/" + funeral.getId() + ".test", objectTree.get( "href" ) );
    }
}
