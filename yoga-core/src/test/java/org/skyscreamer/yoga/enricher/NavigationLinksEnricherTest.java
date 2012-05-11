package org.skyscreamer.yoga.enricher;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.test.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.DummyHttpServletResponse;
import org.skyscreamer.yoga.test.data.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.data.BasicTestDataNode;

public class NavigationLinksEnricherTest
{
    static YogaRequestContext requestContext = new YogaRequestContext( "map",
            new DummyHttpServletRequest(), new DummyHttpServletResponse() );

    @Test
    public void testBasic()
    {
        BasicTestDataLeaf leaf = new BasicTestDataLeaf();
        MapHierarchicalModel model = new MapHierarchicalModel();
        RenderingEvent event = new RenderingEvent( RenderingEventType.POJO_CHILD, model, leaf,
                leaf.getClass(), requestContext, new CoreSelector() );
        new NavigationLinksEnricher().enrich( event );

        Map<String, Object> objectTree = model.getUnderlyingModel();

        Map<String, Object> navLinks = getMap( objectTree, "navigationLinks" );
        Assert.assertNotNull( navLinks );

        Map<String, Object> otherMap = getMap( navLinks, "other" );
        Assert.assertNotNull( otherMap );

        Assert.assertEquals( "/basic-leaf/0.map?selector=:(other)", otherMap.get( "href" ) );
    }

    @Test
    public void testNode()
    {
        BasicTestDataNode node = new BasicTestDataNode();
        node.setId( "foo" );
        MapHierarchicalModel model = new MapHierarchicalModel();
        RenderingEvent event = new RenderingEvent( RenderingEventType.POJO_CHILD, model, node,
                node.getClass(), requestContext, new CoreSelector() );
        new NavigationLinksEnricher().enrich( event );
        System.out.println( model.getUnderlyingModel() );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap( Map<String, Object> map, String key )
    {
        return (Map<String, Object>) map.get( key );
    }
}
