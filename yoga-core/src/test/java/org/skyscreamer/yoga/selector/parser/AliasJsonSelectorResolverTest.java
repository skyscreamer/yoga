package org.skyscreamer.yoga.selector.parser;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class AliasJsonSelectorResolverTest {

    private AliasJsonSelectorResolver resolver;

    @Before
    public void setUp() throws IOException, URISyntaxException {
        resolver = new AliasJsonSelectorResolver();
    }

    @Test
    public void should_transform_simple_json_into_string() throws Exception {
        String res = resolver.resolveSelector("$simple");
        assertEquals("test", res);
    }

    @Test
    public void should_transform_json_with_child_into_string() throws Exception {
        String res = resolver.resolveSelector("$child");
        assertEquals("test,child(value)", res);
    }

    @Test
    public void should_transform_json_with_children_into_string() throws Exception {
        String res = resolver.resolveSelector("$children");
        assertEquals("test,child(value,value2),child2(value3,child3(*))", res);
    }

    @Test
    public void should_transform_json_with_variables() throws Exception {
        String res = resolver.resolveSelector("$variable");
        assertEquals("test,test2", res);
    }

    @Test
    public void should_transform_complexe_json_with_variables() throws Exception {
        String res = resolver.resolveSelector("$variable2");
        assertEquals("testvar,childvar(child,test,child(value,value2),child2(value3,child3(*)))", res);
    }

}

