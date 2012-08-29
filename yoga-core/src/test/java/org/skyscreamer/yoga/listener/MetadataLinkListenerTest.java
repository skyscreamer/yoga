package org.skyscreamer.yoga.listener;

import junit.framework.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.metadata.DefaultMetaDataRegistry;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

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
    public void testMetadataHref()
    {
        String prefixUrl = "/metadata/";
        String fileExtension = "test";
        Album signOfTheTimes = DataGenerator.signOfTheTimes();

        DefaultMetaDataRegistry service = new DefaultMetaDataRegistry();
        service.setRootMetaDataUrl( prefixUrl );
        service.setCoreSelector( new CoreSelector( populatorRegistry ) );

        Map<String,Class<?>> typeMappings = new HashMap<String, Class<?>>();
        typeMappings.put( "album", Album.class );
        service.setTypeMappings( typeMappings );

        MetadataLinkListener metadataLinkListener = new MetadataLinkListener();
        metadataLinkListener.setMetaDataRegistry( service );

        ResultTraverser traverser = new ResultTraverser();
        YogaRequestContext requestContext = new YogaRequestContext( fileExtension,
                new DummyHttpServletRequest(), new DummyHttpServletResponse(), metadataLinkListener );
        Map<String, Object> objectTree = doTraverse( signOfTheTimes, ":", traverser, requestContext );

        Map<String,String> metadataMap = (Map<String,String>) objectTree.get( "metadata" );
        String metadataHref = prefixUrl + "album." + fileExtension;
        Assert.assertEquals( metadataHref, metadataMap.get( "href" ) );
    }
}
