package org.skyscreamer.yoga.mapper;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.selector.CompositeSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataNode;
import org.skyscreamer.yoga.test.model.basic.LeafConfiguration;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResultTraverserTest
{
    static ResultTraverser resultTraverser = new ResultTraverser();
    static EntityConfigurationRegistry registry = new DefaultEntityConfigurationRegistry();
    static YogaRequestContext requestContext;
    static CoreSelector coreSelector;

    @BeforeClass
    public static void setup()
    {
        registry.register( new LeafConfiguration() );
        coreSelector = new CoreSelector( registry );
        requestContext = new YogaRequestContext( "map", new GDataSelectorParser(), new DummyHttpServletRequest(),
                new DummyHttpServletResponse() );
    }

    @Test
    public void testBasicCoreSelector()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        resultTraverser.traverse( input, coreSelector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
    }

    @Test
    public void testBasicFieldSelector()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector selector = new FieldSelector( registry );
        selector.register( "other", new FieldSelector( registry ) );

        resultTraverser.traverse( input, new CompositeSelector( coreSelector, selector ), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
    }

    @Test
    public void testBasicListField()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        List<String> list = Arrays.asList( "someValue", "aSecondValue" );
        input.setRandomStrings( list );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector selector = new FieldSelector( registry );
        selector.register( "randomStrings", new FieldSelector( registry ) );

        resultTraverser.traverse( input, new CompositeSelector( coreSelector, selector ), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( list, objectTree.get( "randomStrings" ) );
    }

    @Test
    public void testBasicCombinedSelctor()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector fieldSelector = new FieldSelector( registry );
        fieldSelector.register( "other", new FieldSelector( registry ) );

        CompositeSelector selector = new CompositeSelector( coreSelector, fieldSelector );
        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNodeCombinedSelector()
    {
        BasicTestDataNode input = new BasicTestDataNode();
        input.setLeaf( new BasicTestDataLeaf() );
        input.setId( "fooId" );

        FieldSelector fieldSelector = new FieldSelector( registry );
        fieldSelector.register( "leaf", new FieldSelector( registry ) );
        CompositeSelector selector = new CompositeSelector( coreSelector, fieldSelector );

        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "fooId", objectTree.get( "id" ) );

        Map<String, Object> childTree = (Map<String, Object>) objectTree.get( "leaf" );
        Assert.assertEquals( 0, childTree.get( "id" ) );
    }

    @Test
    public void testPopulator()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
        FieldSelector fieldSelector = new FieldSelector( registry );
        fieldSelector.register( "someValue", new FieldSelector( registry ) );
        resultTraverser.traverse( input, new CompositeSelector( coreSelector, fieldSelector ), model, requestContext );
        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "someValue", objectTree.get( "someValue" ) );
    }
}
