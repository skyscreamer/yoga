package org.skyscreamer.yoga.listener;

import java.io.IOException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

public class NavigationLinksListenerTest
{
    static YogaRequestContext requestContext = new YogaRequestContext( "map", new SelectorResolver(),
            new DummyHttpServletRequest(), new DummyHttpServletResponse() );

    @Test
    public void testBasic() throws IOException
    {
        BasicTestDataLeaf leaf = new BasicTestDataLeaf();
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
        RenderingEvent<BasicTestDataLeaf> event = new RenderingEvent<BasicTestDataLeaf>( RenderingEventType.POJO_CHILD, model, leaf,
        		BasicTestDataLeaf.class, requestContext, new CoreSelector(
                        new DefaultEntityConfigurationRegistry() ) );
        new NavigationLinksListener().eventOccurred( event );

        Map<String, Object> objectTree = model.getUnderlyingModel();

        Map<String, Object> navLinks = getMap( objectTree, "navigationLinks" );
        Assert.assertNotNull( navLinks );

        Map<String, Object> otherMap = getMap( navLinks, "other" );
        Assert.assertNotNull( otherMap );

        Assert.assertEquals( "/basic-leaf/0.map?selector=other", otherMap.get( "href" ) );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap( Map<String, Object> map, String key )
    {
        return (Map<String, Object>) map.get( key );
    }
}
