package org.skyscreamer.yoga.mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.selector.CompositeSelector;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataNode;
import org.skyscreamer.yoga.test.model.basic.LeafConfiguration;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

public class ResultTraverserTest
{
    ResultTraverser resultTraverser = new ResultTraverser();
    EntityConfigurationRegistry registry = new DefaultEntityConfigurationRegistry();
    YogaRequestContext requestContext;
    SelectorResolver resolver = new SelectorResolver();

    @Before
    public void setup()
    {
        registry.register( new LeafConfiguration() );
        resolver.setEntityConfigurationRegistry( registry );
        requestContext = new YogaRequestContext( "map", resolver , new DummyHttpServletRequest(),
                new DummyHttpServletResponse() );
    }

    @Test
    public void testBasicSelector() throws IOException
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        resultTraverser.traverse( input, resolver.getBaseSelector(), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
    }

    @Test
    public void testBasicFieldSelector() throws IOException
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        resultTraverser.traverse( input, resolver.resolveSelector( "other" ), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
    }

    @Test
    public void testStarFieldSelector() throws IOException
    {
        resolver.setStarResolvesToAll( true );
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        resultTraverser.traverse( input, resolver.resolveSelector( "*" ), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
        Assert.assertEquals( 0, objectTree.get( "id" ) );
        Assert.assertEquals( "customValue", objectTree.get( "someField" ) );
    }
    
    
    @Test
    public void testBasicListField() throws IOException
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        List<String> list = Arrays.asList( "someValue", "aSecondValue" );
        input.setRandomStrings( list );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector selector = new FieldSelector();
        selector.register( "randomStrings", new FieldSelector() );

        resultTraverser.traverse( input, new CompositeSelector( resolver.getBaseSelector(), selector ), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( list, objectTree.get( "randomStrings" ) );
    }

    @Test
    public void testBasicMapField() throws IOException
    {
        HashMap<Object,Object> map = new HashMap<Object, Object>();
        map.put("someKey", "someValue");
        map.put("anotherKey", "anotherValue");
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setId(10);
        input.setName("name");
        input.setOther("other");
        map.put("aThirdKey", input);
        
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector selector = new FieldSelector();
        selector.register( "anotherKey", new FieldSelector() );
        selector.register( "aThirdKey", new FieldSelector() );

        resultTraverser.traverse( map, new CompositeSelector( resolver.getBaseSelector(), selector ), model, requestContext );
        Map<String, Object> objectTree = model.getUnderlyingModel();
        
        // "someKey" should not be selected from map
        Assert.assertEquals( null, objectTree.get( "someKey" ) );
        
        // "anotherKey" should be selected from map and has primitive value
        Assert.assertEquals( "anotherValue", objectTree.get( "anotherKey" ) );
        
        // "aThirdKey" should be selected but only @Core fields of pojo
        Map<?,?> actualPojo = (Map<?,?>) objectTree.get("aThirdKey");
        Assert.assertEquals( input.getId(), actualPojo.get("id"));
        Assert.assertEquals( input.getName(), actualPojo.get("name"));
        Assert.assertEquals(null, actualPojo.get("other"));
    }

    @Test
    public void testNullValuesInList() throws IOException {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        List<String> list = Arrays.asList( "someValue", null, "aThirdValue" );
        input.setRandomStrings( list );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector selector = new FieldSelector();
        selector.register( "randomStrings", new FieldSelector() );

        resultTraverser.traverse( input, new CompositeSelector( resolver.getBaseSelector(), selector ), model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( list, objectTree.get( "randomStrings" ) );
    }

    @Test
    public void testBasicCombinedSelctor() throws IOException
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.register( "other", new FieldSelector() );

        CompositeSelector selector = new CompositeSelector( resolver.getBaseSelector(), fieldSelector );
        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNodeCombinedSelector() throws IOException
    {
        BasicTestDataNode input = new BasicTestDataNode();
        input.setLeaf( new BasicTestDataLeaf() );
        input.setId( "fooId" );

        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.register( "leaf", new FieldSelector() );
        CompositeSelector selector = new CompositeSelector( resolver.getBaseSelector(), fieldSelector );

        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();

        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "fooId", objectTree.get( "id" ) );

        Map<String, Object> childTree = (Map<String, Object>) objectTree.get( "leaf" );
        Assert.assertEquals( 0, childTree.get( "id" ) );
    }

    @Test
    public void testConfiguration() throws IOException
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.register( "someField", new FieldSelector() );
        resultTraverser.traverse( input, new CompositeSelector( resolver.getBaseSelector(), fieldSelector ), model, requestContext );
        Map<String, Object> objectTree = model.getUnderlyingModel();
        Assert.assertEquals( "customValue", objectTree.get( "someField" ) );
    }
}
