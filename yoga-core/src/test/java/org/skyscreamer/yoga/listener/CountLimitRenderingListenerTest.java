package org.skyscreamer.yoga.listener;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.yoga.exceptions.EntityCountExceededException;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ObjectListHierarchicalModelImpl;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.test.model.basic.BasicTestDataLeaf;
import org.skyscreamer.yoga.test.util.DummyHttpServletRequest;
import org.skyscreamer.yoga.test.util.DummyHttpServletResponse;

public class CountLimitRenderingListenerTest
{
    static ResultTraverser resultTraverser;
    static int MAX_RESULTS = 100;
    static YogaRequestContext requestContext;

    @BeforeClass
    public static void setup()
    {
        resultTraverser = new ResultTraverser();
        requestContext = new YogaRequestContext( "map", new SelectorResolver(), new DummyHttpServletRequest(),
                new DummyHttpServletResponse(), new CountLimitRenderingListener( MAX_RESULTS ) );
    }

    @Test(expected = EntityCountExceededException.class)
    public void testLotsOfData() throws IOException
    {
        ArrayList<BasicTestDataLeaf> input = new ArrayList<BasicTestDataLeaf>();
        for (int i = 0; i < MAX_RESULTS + 1; i++)
        {
            input.add( new BasicTestDataLeaf() );
        }
        ObjectListHierarchicalModelImpl model = new ObjectListHierarchicalModelImpl();
        resultTraverser.traverse( input, new CoreSelector(), model, requestContext );
    }
}
