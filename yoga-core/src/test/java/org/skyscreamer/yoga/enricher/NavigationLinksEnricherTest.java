package org.skyscreamer.yoga.enricher;

import junit.framework.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataNode;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

import java.util.Map;

public class NavigationLinksEnricherTest
{
    static YogaRequestContext requestContext = new YogaRequestContext( "map",
            new DummyHttpServletRequest(), new DummyHttpServletResponse(), new DefaultFieldPopulatorRegistry() );

    @Test
    public void testBasic()
    {
        BasicTestDataLeaf leaf = new BasicTestDataLeaf();
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
        RenderingEvent event = new RenderingEvent( RenderingEventType.POJO_CHILD, model, leaf,
                leaf.getClass(), requestContext, new CoreSelector( new DefaultFieldPopulatorRegistry() ) );
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
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
        RenderingEvent event = new RenderingEvent( RenderingEventType.POJO_CHILD, model, node,
                node.getClass(), requestContext, new CoreSelector( new DefaultFieldPopulatorRegistry() ) );
        new NavigationLinksEnricher().enrich( event );
        System.out.println( model.getUnderlyingModel() );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap( Map<String, Object> map, String key )
    {
        return (Map<String, Object>) map.get( key );
    }
}
