package org.skyscreamer.yoga.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulatorRenderingListenerAdapter;
import org.skyscreamer.yoga.selector.CompositeSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.test.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.DummyHttpServletResponse;
import org.skyscreamer.yoga.test.data.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.data.BasicTestDataNode;

public class ResultTraverserTest
{
    static ResultTraverser resultTraverser;
    static YogaRequestContext requestContext;

    public static class LeafPopulator
    {
        @ExtraField("someValue")
        public String getSomeValue()
        {
            return "someValue";
        }
    };

    @BeforeClass
    public static void setup()
    {
        resultTraverser = new ResultTraverser();

        DefaultFieldPopulatorRegistry registry = new DefaultFieldPopulatorRegistry();

        registry.register( BasicTestDataLeaf.class, new LeafPopulator() );

        RenderingListener listener = new FieldPopulatorRenderingListenerAdapter( registry,
                resultTraverser );
        requestContext = new YogaRequestContext( "map", new DummyHttpServletRequest(),
                new DummyHttpServletResponse(), listener );
    }

    @Test
    public void testBasicCoreSelector()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        MapHierarchicalModel model = new MapHierarchicalModel();

        resultTraverser.traverse( input, new CoreSelector(), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
    }

    @Test
    public void testBasicFieldSelector()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        MapHierarchicalModel model = new MapHierarchicalModel();

        FieldSelector selector = new FieldSelector();
        selector.register( "other", new FieldSelector() );

        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
    }

    @Test
    public void testBasicListField()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        List<String> list = Arrays.asList( "someValue", "aSecondValue" );
        input.setRandomStrings( list );
        MapHierarchicalModel model = new MapHierarchicalModel();

        FieldSelector selector = new FieldSelector();
        selector.register( "randomStrings", new FieldSelector() );

        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( list, objectTree.get( "randomStrings" ) );
    }

    @Test
    public void testBasicCombinedSelctor()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        MapHierarchicalModel model = new MapHierarchicalModel();

        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.register( "other", new FieldSelector() );

        CompositeSelector selector = new CompositeSelector( fieldSelector, new CoreSelector() );
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

        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.register( "leaf", new FieldSelector() );
        CompositeSelector selector = new CompositeSelector( fieldSelector, new CoreSelector() );

        MapHierarchicalModel model = new MapHierarchicalModel();

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
        MapHierarchicalModel model = new MapHierarchicalModel();
        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.register( "someValue", new FieldSelector() );
        resultTraverser.traverse( input, fieldSelector, model, requestContext );
        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "someValue", objectTree.get( "someValue" ) );
    }
}
