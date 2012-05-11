package org.skyscreamer.yoga.limits;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.yoga.exceptions.EntityCountExceededException;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.test.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.DummyHttpServletResponse;
import org.skyscreamer.yoga.test.data.BasicTestDataLeaf;

public class CountLimitRenderingListenerTest
{
    static ResultTraverser resultTraverser;
    static int MAX_RESULTS = 100;
    static YogaRequestContext requestContext;

    @BeforeClass
    public static void setup()
    {
        resultTraverser = new ResultTraverser();
        requestContext = new YogaRequestContext( "map", new DummyHttpServletRequest(),
                new DummyHttpServletResponse(), new CountLimitRenderingListener( MAX_RESULTS ) );
    }

    @Test(expected = EntityCountExceededException.class)
    public void testLotsOfData()
    {
        ArrayList<BasicTestDataLeaf> input = new ArrayList<BasicTestDataLeaf>();
        for (int i = 0; i < MAX_RESULTS + 1; i++)
        {
            input.add( new BasicTestDataLeaf() );
        }
        ListHierarchicalModel model = new ListHierarchicalModel();
        resultTraverser.traverse( input, new CoreSelector(), model, requestContext );
    }
}
