package org.skyscreamer.yoga.enricher;

import junit.framework.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.test.DummyHttpServletResponse;
import org.skyscreamer.yoga.test.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.data.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.data.BasicTestDataNode;

import java.util.Map;

public class NavigationLinksEnricherTest
{
    NavigationLinksEnricher enricher = new NavigationLinksEnricher();
    static YogaRequestContext requestContext = new YogaRequestContext( "map", new DummyHttpServletRequest(), new DummyHttpServletResponse() );

    @Test
    public void testBasic()
    {
        BasicTestDataLeaf leaf = new BasicTestDataLeaf();
        MapHierarchicalModel model = new MapHierarchicalModel();
        YogaInstanceContext<BasicTestDataLeaf> entityContext = new YogaInstanceContext<BasicTestDataLeaf>(
                leaf, BasicTestDataLeaf.class, new CoreSelector(), model,
                requestContext );
        enricher.enrich( entityContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();

        Map<String, Object> navLinks = getMap( objectTree, "navigationLinks" );
        Assert.assertNotNull( navLinks );

        Map<String, Object> otherMap = getMap( navLinks, "other" );
        Assert.assertNotNull( otherMap );

        Assert.assertEquals( "other", otherMap.get( "name" ) );
        Assert.assertEquals( "/basic-leaf/0.map?selector=:(other)", otherMap.get( "href" ) );
        System.out.println( objectTree );
    }

    @Test
    public void testNode()
    {
        BasicTestDataNode node = new BasicTestDataNode();
        node.setId( "foo" );
        MapHierarchicalModel model = new MapHierarchicalModel();
        YogaInstanceContext<BasicTestDataNode> entityContext = new YogaInstanceContext<BasicTestDataNode>(
                node, BasicTestDataNode.class, new CoreSelector(), model, requestContext );
        enricher.enrich( entityContext );
        System.out.println( model.getUnderlyingModel() );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap( Map<String, Object> map, String key )
    {
        return (Map<String, Object>) map.get( key );
    }
}
