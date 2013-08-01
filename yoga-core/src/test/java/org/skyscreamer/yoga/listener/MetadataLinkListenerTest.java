package org.skyscreamer.yoga.listener;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.metadata.DefaultMetaDataRegistry;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;

/**
 * User: corby
 * Date: 6/7/12
 */
public class MetadataLinkListenerTest extends AbstractTraverserTest
{
    @SuppressWarnings("unchecked")
	@Test
    // Add the MetadataLinkListener to the listener chain. The output will render an href to view the metadata
    // for the album object.
    // "metadata": {
    //     "href": "/metadata/album.test"
    //  }

    public void testMetadataHref()
    {
        String prefixUrl = "/metadata/";
        String fileExtension = "test";
        Album signOfTheTimes = DataGenerator.signOfTheTimes();

        DefaultMetaDataRegistry service = new DefaultMetaDataRegistry();
        service.setRootMetaDataUrl( prefixUrl );
        service.setCoreSelector( getCoreSelector() );

        Map<String,Class<?>> typeMappings = new HashMap<String, Class<?>>();
        typeMappings.put( "album", Album.class );
        service.setTypeMappings( typeMappings );

        MetadataLinkListener metadataLinkListener = new MetadataLinkListener();
        metadataLinkListener.setMetaDataRegistry( service );

        ResultTraverser traverser = new ResultTraverser();
        Map<String, Object> objectTree = doTraverse( signOfTheTimes, "", traverser, metadataLinkListener );

        Map<String,String> metadataMap = (Map<String,String>) objectTree.get( "metadata" );
        String metadataHref = prefixUrl + "album." + fileExtension;
        Assert.assertEquals( metadataHref, metadataMap.get( "href" ) );
    }

}
