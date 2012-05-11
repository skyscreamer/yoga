package org.skyscreamer.yoga.selector;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulatorRenderingListenerAdapter;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.User;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

/**
 * User: corby Date: 5/6/12
 */
public class CoreFieldsTest extends AbstractTraverserTest
{
    @Test
    // The User object has a @Core annotation on the ID and Name fields. Pass in
    // an empty selector and a null selector,
    // and verify that only the ID and Name fields are returned.
    public void testCoreAnnotations() throws ParseSelectorException
    {
        User solomon = DataGenerator.solomon();
        ResultTraverser traverser = new ResultTraverser();

        Map<String, Object> objectTree = doTraverse( solomon, ":", traverser, _simpleContext );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( solomon.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( solomon.getName(), objectTree.get( "name" ) );
    }

    @Test
    // Use an AlbumFieldPopulator that defines ID and Title as core fields. Pass
    // in an empty selector and a null
    // selector, and verify that only the ID and Title fields are returned.
    public void testNullCorePopulatorFields()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = new ResultTraverser();
        MapSelector selector = new MapSelector();
        selector.register( Album.class, "id", "title" );
        Map<String, Object> objectTree = doTraverse( funeral, traverser, _simpleContext, selector );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( funeral.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( funeral.getTitle(), objectTree.get( "title" ) );
    }

    /**
     * Use an AlbumFieldPopulator that defines ID and Title as core fields. Pass
     * in an empty selector and a null selector, and verify that only the ID and
     * Title fields are returned.
     */
    @Test
    public void testEmptyCorePopulatorFields()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = new ResultTraverser();
        MapSelector selector = new MapSelector();
        selector.register( Album.class, "id", "title" );
        Map<String, Object> objectTree = doTraverse( funeral, traverser, _simpleContext, selector );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( funeral.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( funeral.getTitle(), objectTree.get( "title" ) );
    }

    /**
     * Use a UserFieldPopulator that defines a complex type (List of Artists) as
     * a core field. Also, ensure that core fields defined by both the
     * FieldPopulator and the annotations are returned.
     */
    @Test
    public void testComplexCoreFields()
    {
        User carter = DataGenerator.carter();
        carter.getFavoriteArtists().add( DataGenerator.neutralMilkHotel() );
        carter.getFavoriteArtists().add( DataGenerator.arcadeFire() );
        ResultTraverser traverser = new ResultTraverser();
        MapSelector selector = new MapSelector();
        selector.register( User.class, "id", "favoriteArtists" );

        Map<String, Object> objectTree = doTraverse( carter, traverser, _simpleContext, new CompositeSelector( selector, new CoreSelector() ) );
        Assert.assertTrue( objectTree.size() >= 2 );
        Assert.assertEquals( carter.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( carter.getName(), objectTree.get( "name" ) );

        List<Map<String, Object>> favoriteArtists = getList( objectTree, "favoriteArtists" );
        Assert.assertNotNull( favoriteArtists );
        Assert.assertEquals( 2, favoriteArtists.size() );
        Map<String, Object> neutralMap = findItem( favoriteArtists, "name", "Neutral Milk Hotel" );
        Assert.assertEquals( DataGenerator.neutralMilkHotel().getId(), neutralMap.get( "id" ) );
        Map<String, Object> arcadeMap = findItem( favoriteArtists, "name", "Arcade Fire" );
        Assert.assertEquals( DataGenerator.arcadeFire().getId(), arcadeMap.get( "id" ) );
    }

    public YogaRequestContext getContextForPopulator( Object populator )
    {
        FieldPopulatorRenderingListenerAdapter listener = new FieldPopulatorRenderingListenerAdapter();
        listener.setFieldPopulatorRegistry( new DefaultFieldPopulatorRegistry( populator ) );
        YogaRequestContext context = new YogaRequestContext( "map", new DummyHttpServletRequest(),
                new DummyHttpServletResponse(), listener );
        return context;
    }
}
