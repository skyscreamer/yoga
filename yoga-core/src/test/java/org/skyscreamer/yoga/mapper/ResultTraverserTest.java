package org.skyscreamer.yoga.mapper;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.exceptions.EntityCountExceededException;
import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulatorSupport;
import org.skyscreamer.yoga.selector.CombinedSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.test.DummyHttpServletResponse;
import org.skyscreamer.yoga.test.data.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.data.BasicTestDataNode;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;

import java.util.*;

public class ResultTraverserTest
{
    static ResultTraverser resultTraverser;
    static int MAX_RESULTS = 100;
    static YogaRequestContext requestContext;

    @BeforeClass
    public static void setup()
    {
        resultTraverser = new ResultTraverser();
        YogaInstanceContextFactory instanceContextFactory = new YogaInstanceContextFactory();
        instanceContextFactory.setClassFinderStrategy( new DefaultClassFinderStrategy() );

        resultTraverser.setInstanceContextFactory( instanceContextFactory );
        instanceContextFactory.setMaxEntities( MAX_RESULTS );

        DefaultFieldPopulatorRegistry registry = new DefaultFieldPopulatorRegistry();
        registry.register( BasicTestDataLeaf.class, new FieldPopulatorSupport()
        {
            @SuppressWarnings("unused")
            @ExtraField("someValue")
            public String getSomeValue()
            {
                return "someValue";
            }
        } );
        instanceContextFactory.setFieldPopulatorRegistry( registry );

        requestContext = new YogaRequestContext( "map", new DummyHttpServletResponse() );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBasicCoreSelector()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        MapHierarchicalModel model = new MapHierarchicalModel();

        resultTraverser.traverse( input, new CoreSelector(), model, requestContext );

        Map<String, Object> objectTree = model.getObjectTree();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
        Assert.assertEquals( sort( Arrays.asList( "id", "name", "other", "someValue", "randomStrings" ) ),
                sort( (List<String>) objectTree.get( "definition" ) ) );
        Assert.assertEquals( "/basic-leaf/0.map", objectTree.get( "href" ) );
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends Comparable> List<T> sort( List<T> list )
    {
        Collections.sort( list );
        return list;
    }

    @Test
    public void testBasicFieldSelctor()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        MapHierarchicalModel model = new MapHierarchicalModel();

        FieldSelector selector = new FieldSelector();
        selector.addField( "other", new FieldSelector() );

        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getObjectTree();
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
        Assert.assertEquals( "/basic-leaf/0.map", objectTree.get( "href" ) );
    }

    @Test
    public void testBasicListField()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        List<String> list = Arrays.asList( "someValue", "aSecondValue" );
        input.setRandomStrings( list );
        MapHierarchicalModel model = new MapHierarchicalModel();

        FieldSelector selector = new FieldSelector();
        selector.addField( "randomStrings", new FieldSelector() );

        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getObjectTree();
        Assert.assertEquals( list, objectTree.get( "randomStrings" ) );
    }

    @Test
    public void testBasicCombinedSelctor()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        input.setOther( "someValue" );
        MapHierarchicalModel model = new MapHierarchicalModel();

        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.addField( "other", new FieldSelector() );

        CombinedSelector selector = new CombinedSelector( fieldSelector, new CoreSelector() );
        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getObjectTree();
        Assert.assertEquals( 0, objectTree.get( "id" ) );
        Assert.assertEquals( "someValue", objectTree.get( "other" ) );
        Assert.assertEquals( "/basic-leaf/0.map", objectTree.get( "href" ) );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNodeCombinedSelector()
    {
        BasicTestDataNode input = new BasicTestDataNode();
        input.setLeaf( new BasicTestDataLeaf() );
        input.setId( "fooId" );

        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.addField( "leaf", new FieldSelector() );

        MapHierarchicalModel model = new MapHierarchicalModel();

        CombinedSelector selector = new CombinedSelector( fieldSelector, new CoreSelector() );
        resultTraverser.traverse( input, selector, model, requestContext );

        Map<String, Object> objectTree = model.getObjectTree();
        Assert.assertEquals( "fooId", objectTree.get( "id" ) );
        Assert.assertEquals( "/basic-node/fooId.map", objectTree.get( "href" ) );

        Map<String, Object> childTree = (Map<String, Object>) objectTree.get( "leaf" );
        Assert.assertEquals( 0, childTree.get( "id" ) );
        Assert.assertEquals( "/basic-leaf/0.map", childTree.get( "href" ) );
    }

    @Test
    public void testPopulator()
    {
        BasicTestDataLeaf input = new BasicTestDataLeaf();
        MapHierarchicalModel model = new MapHierarchicalModel();
        FieldSelector fieldSelector = new FieldSelector();
        fieldSelector.addField( "someValue", new FieldSelector() );
        resultTraverser.traverse( input, fieldSelector, model, requestContext );
        Map<String, Object> objectTree = model.getObjectTree();
        Assert.assertEquals( "someValue", objectTree.get( "someValue" ) );
        Assert.assertEquals( "/basic-leaf/0.map", objectTree.get( "href" ) );
    }

    @Test(expected = EntityCountExceededException.class)
    public void testLotsOfData()
    {
        ArrayList<BasicTestDataLeaf> input = new ArrayList<BasicTestDataLeaf>();
        for ( int i = 0; i < MAX_RESULTS + 1; i++ )
        {
            input.add( new BasicTestDataLeaf() );
        }
        ListHierarchicalModel model = new ListHierarchicalModel();
        resultTraverser.traverse( input, new CoreSelector(), model, requestContext );
    }
}
