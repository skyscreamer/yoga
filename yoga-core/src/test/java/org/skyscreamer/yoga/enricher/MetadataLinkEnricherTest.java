package org.skyscreamer.yoga.enricher;

import junit.framework.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.metadata.MapMetaDataServiceImpl;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.Artist;
import org.skyscreamer.yoga.test.model.extended.Song;
import org.skyscreamer.yoga.test.model.extended.User;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * User: corby
 * Date: 6/7/12
 */
public class MetadataLinkEnricherTest extends AbstractTraverserTest
{
    @Test
    // Add the MetadataLinkEnricher to the enricher chain. The output will render an href to view the metadata
    // for the album object.
    public void testMetadataHref()
    {
        String prefixUrl = "/metadata/";
        String fileExtension = "test";
        Album signOfTheTimes = DataGenerator.signOfTheTimes();

        MapMetaDataServiceImpl service = new MapMetaDataServiceImpl();
        service.setRootMetaDataUrl( prefixUrl );
        service.setCoreSelector( new CoreSelector( populatorRegistry ) );

        Map<String,Class<?>> typeMappings = new HashMap<String, Class<?>>();
        typeMappings.put( "album", Album.class );
        service.setTypeMappings( typeMappings );

        MetadataLinkEnricher metadataLinkEnricher = new MetadataLinkEnricher();
        metadataLinkEnricher.setMetaDataService( service );

        ResultTraverser traverser = new ResultTraverser();
        YogaRequestContext requestContext = new YogaRequestContext( fileExtension,
                new DummyHttpServletRequest(), new DummyHttpServletResponse(), metadataLinkEnricher );
        Map<String, Object> objectTree = doTraverse( signOfTheTimes, ":", traverser, requestContext );

        Map<String,String> metadataMap = (Map<String,String>) objectTree.get( "metadata" );
        String metadataHref = prefixUrl + "album." + fileExtension;
        Assert.assertEquals( metadataHref, metadataMap.get( "href" ) );
    }
}
