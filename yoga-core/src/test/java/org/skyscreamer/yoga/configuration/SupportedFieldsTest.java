package org.skyscreamer.yoga.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.test.model.basic.DataGenerator;
import org.skyscreamer.yoga.test.model.extended.Album;
import org.skyscreamer.yoga.test.model.extended.AlbumEntityConfiguration;
import org.skyscreamer.yoga.test.util.AbstractTraverserTest;

import java.util.Map;

/**
 * User: corby
 * Date: 5/14/12
 */
@SuppressWarnings("unchecked")
public class SupportedFieldsTest extends AbstractTraverserTest
{
    // Year is a visible property on the Album object, but the AlbumEntityConfiguration enumerates supported fields,
    // which exclude year. Year should not be returned, even if it is named in the selector
    @Test
    public void testSelectUnsupportedField()
    {
        Album chasingProphecy = DataGenerator.chasingProphecy();
        ResultTraverser traverser = new ResultTraverser();
        getEntityConfigurationRegistry().register( new AlbumEntityConfiguration() );

        Map<String,Object> objectTree = doTraverse( chasingProphecy, "id,title,year,artist", traverser );
        Assert.assertEquals( 3, objectTree.size() );
        Assert.assertEquals( chasingProphecy.getId(), objectTree.get( "id" ) );
        Assert.assertEquals( chasingProphecy.getTitle(), objectTree.get( "title" ) );
        Map<String,Object> eighthDay = (Map<String, Object>) objectTree.get( "artist" );
        Assert.assertEquals( DataGenerator.eigthDay().getName(), eighthDay.get( "name" ) );
    }
}
