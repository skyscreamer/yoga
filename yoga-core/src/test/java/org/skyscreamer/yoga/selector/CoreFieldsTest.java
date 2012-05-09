package org.skyscreamer.yoga.selector;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.test.AbstractTraverserTest;
import org.skyscreamer.yoga.test.data.DataGenerator;
import org.skyscreamer.yoga.test.model.Album;
import org.skyscreamer.yoga.test.model.AlbumFieldPopulator;
import org.skyscreamer.yoga.test.model.User;
import org.skyscreamer.yoga.test.model.UserFieldPopulatorWithArtistsCore;

/**
 * User: corby
 * Date: 5/6/12
 */
public class CoreFieldsTest extends AbstractTraverserTest
{
    @Test
    // The User object has a @Core annotation on the ID and Name fields. Pass in an empty selector and a null selector,
    // and verify that only the ID and Name fields are returned.
    public void testCoreAnnotations() throws ParseSelectorException
    {
        User solomon = DataGenerator.solomon();
        ResultTraverser traverser = traverserNoEnrichers();

        doTraverse( solomon, ":", traverser, _simpleContext );
        Assert.assertEquals( 2, _objectTree.size() );
        Assert.assertEquals( solomon.getId(), _objectTree.get( "id" ) );
        Assert.assertEquals( solomon.getName(), _objectTree.get( "name" ) );

        doTraverse( solomon, null, traverser, _simpleContext );
        Assert.assertEquals( 2, _objectTree.size() );
        Assert.assertEquals( solomon.getId(), _objectTree.get( "id" ) );
        Assert.assertEquals( solomon.getName(), _objectTree.get( "name" ) );
    }

    @Test
    // Use an AlbumFieldPopulator that defines ID and Title as core fields. Pass in an empty selector and a null
    // selector, and verify that only the ID and Title fields are returned.
    public void testCorePopulatorFields()
    {
        Album funeral = DataGenerator.funeral();
        ResultTraverser traverser = traverserNoEnrichers();
        addFieldPopulators( traverser, new AlbumFieldPopulator() );

        doTraverse( funeral, ":", traverser, _simpleContext );
        Assert.assertEquals( 2, _objectTree.size() );
        Assert.assertEquals( funeral.getId(), _objectTree.get( "id" ) );
        Assert.assertEquals( funeral.getTitle(), _objectTree.get( "title" ) );

        doTraverse( funeral, null, traverser, _simpleContext );
        Assert.assertEquals( 2, _objectTree.size() );
        Assert.assertEquals( funeral.getId(), _objectTree.get( "id" ) );
        Assert.assertEquals( funeral.getTitle(), _objectTree.get( "title" ) );
    }

    @Test
    // Use a UserFieldPopulator that defines a complex type (List of Artists) as a core field. Also, ensure that
    // core fields defined by both the FieldPopulator and the annotations are returned.
    public void testComplexCoreFields()
    {
        User carter = DataGenerator.carter();
        carter.getFavoriteArtists().add( DataGenerator.neutralMilkHotel() );
        carter.getFavoriteArtists().add( DataGenerator.arcadeFire() );
        ResultTraverser traverser = traverserNoEnrichers();
        addFieldPopulators( traverser, new UserFieldPopulatorWithArtistsCore() );

        doTraverse( carter, ":", traverser, _simpleContext );
        Assert.assertEquals( 3, _objectTree.size() );
        Assert.assertEquals( carter.getId(), _objectTree.get( "id" ) );
        Assert.assertEquals( carter.getName(), _objectTree.get( "name" ) );

        List<Map<String,Object>> favoriteArtists = getList( _objectTree, "favoriteArtists" );
        Assert.assertEquals( 2, favoriteArtists.size() );
        Map<String,Object> neutralMap = findItem( favoriteArtists, "name", "Neutral Milk Hotel" );
        Assert.assertEquals( DataGenerator.neutralMilkHotel().getId(), neutralMap.get( "id" ) );
        Map<String,Object> arcadeMap = findItem( favoriteArtists, "name", "Arcade Fire" );
        Assert.assertEquals( DataGenerator.arcadeFire().getId(), arcadeMap.get( "id" ) );
    }
}
