package org.skyscreamer.yoga.selector;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.AlbumEntityConfiguration;
import org.skyscreamer.yoga.test.model.extended.User;
import org.skyscreamer.yoga.test.model.extended.UserEntityConfigurationWithArtistCoreField;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;

import java.util.List;
import java.util.Map;

/**
 * User: corby Date: 5/6/12
 */
public class CoreFieldsTest extends AbstractTraverserTest
{
    // The User object has a @Core annotation on the ID and Name fields. Pass in
    // an empty selector and a null selector,
    // and verify that only the ID and Name fields are returned.
    @Test
    public void testCoreAnnotations() throws ParseSelectorException
    {
        User solomon = DataGenerator.solomon();
        ResultTraverser traverser = new ResultTraverser();

        Map<String, Object> objectTree = doTraverse( solomon, ":", traverser );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( solomon.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( solomon.getName(), objectTree.get( "name" ) );
    }

    // Use an AlbumEntityConfiguration that defines ID and Title as core fields. Pass
    // in an empty selector and a null
    // selector, and verify that only the ID and Title fields are returned.
    @Test
    public void testNullCoreEntityConfigurationFields()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = new ResultTraverser();
        getEntityConfigurationRegistry().register(new AlbumEntityConfiguration());
        Map<String, Object> objectTree = doTraverse( funeral, null, traverser );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( funeral.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( funeral.getTitle(), objectTree.get( "title" ) );
    }

    /**
     * Use an AlbumEntityConfiguration that defines ID and Title as core fields. Pass
     * in an empty selector and a null selector, and verify that only the ID and
     * Title fields are returned.
     */
    @Test
    public void testEmptyCoreEntityConfigurationFields()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = new ResultTraverser();
        getEntityConfigurationRegistry().register( new AlbumEntityConfiguration() );
        Map<String, Object> objectTree = doTraverse( funeral, ":", traverser );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( funeral.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( funeral.getTitle(), objectTree.get( "title" ) );
    }

    /**
     * Use a UserConfiguration that defines a complex type (List of Artists) as
     * a core field. Also, ensure that core fields defined by both the
     * YogaEntityConfiguration and the annotations are returned.
     */
    @Test
    public void testComplexCoreFields()
    {
        User carter = DataGenerator.carter();
        carter.getFavoriteArtists().add( DataGenerator.neutralMilkHotel() );
        carter.getFavoriteArtists().add( DataGenerator.arcadeFire() );
        ResultTraverser traverser = new ResultTraverser();
        getEntityConfigurationRegistry().register( new UserEntityConfigurationWithArtistCoreField() );

        Map<String, Object> objectTree = doTraverse( carter, ":", traverser );
        Assert.assertEquals( 2, objectTree.size() );
        Assert.assertEquals( carter.getId(), objectTree.get( "id" ) );

        List<Map<String, Object>> favoriteArtists = getList( objectTree, "favoriteArtists" );
        Assert.assertNotNull( favoriteArtists );
        Assert.assertEquals( 2, favoriteArtists.size() );
        Map<String, Object> neutralMap = findItem( favoriteArtists, "name", "Neutral Milk Hotel" );
        Assert.assertEquals( DataGenerator.neutralMilkHotel().getId(), neutralMap.get( "id" ) );
        Map<String, Object> arcadeMap = findItem( favoriteArtists, "name", "Arcade Fire" );
        Assert.assertEquals( DataGenerator.arcadeFire().getId(), arcadeMap.get( "id" ) );
    }
}
