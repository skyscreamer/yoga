package org.skyscreamer.yoga.selector.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import org.skyscreamer.yoga.util.JsonObjectNode;

public class AliasJsonSelectorParserTest {

    private AliasJsonSelectorParser parser;

    @Before
    public void setUp() {
        parser = new AliasJsonSelectorParser();
    }

    @Test
    public void should_transform_simple_json_into_alias() throws Exception {
        Map<String, JsonObjectNode> res = parser.extractYogaAlias(new JsonFactory().createParser("{\"$simple\":[\"test\"]}"));
        assertNotNull(res.get("$simple"));
        assertEquals(true, res.get("$simple").isValid());
        assertEquals(true, res.get("$simple").isAlias());
        assertEquals("test", res.get("$simple").toString());
    }

    @Test
    public void should_transform_simple_json_into_variable() throws Exception {
        Map<String, JsonObjectNode> res = parser.extractYogaAlias(new JsonFactory().createParser("{\"@simple\":[\"test\"]}"));
        assertNotNull(res.get("@simple"));
        assertEquals(true, res.get("@simple").isValid());
        assertEquals(true, res.get("@simple").isVariable());
        assertEquals("test", res.get("@simple").toString());
    }

    @Test(expected=RuntimeException.class)
    public void should_throw_exception_if_type_is_not_valid() throws Exception {
        parser.extractYogaAlias(new JsonFactory().createParser("{\"simple\":[\"test\"]}"));
    }

    @Test
    public void should_transform_children_json() throws Exception {
        Map<String, JsonObjectNode> res = parser.extractYogaAlias(new JsonFactory().createParser("{\"@simple\":[{\"test\": [\"test\"]}, \"test\"]}"));
        assertNotNull(res.get("@simple"));
        assertEquals(true, res.get("@simple").isValid());
        assertEquals(true, res.get("@simple").isVariable());
        assertEquals("test(test),test", res.get("@simple").toString());
    }

    @Test
    public void should_transform_complexe_children_json() throws Exception {
        Map<String, JsonObjectNode> res = parser.extractYogaAlias(new JsonFactory().createParser("{\"@simple\":[{\"test\": [\"test\", {\"test2\": [\"test2\"]}]}, \"test\"]}"));
        assertNotNull(res.get("@simple"));
        assertEquals(true, res.get("@simple").isValid());
        assertEquals(true, res.get("@simple").isVariable());
        assertEquals("test(test,test2(test2)),test", res.get("@simple").toString());
    }

    @Test
    public void should_transform_2_brothers() throws Exception {
        Map<String, JsonObjectNode> res = parser.extractYogaAlias(new JsonFactory().createParser("{\"@simple\":[{\"test\": [{\"test2\": [\"test2\"], \"test3\": [\"test3\"]}]}, \"test\"]}"));
        assertNotNull(res.get("@simple"));
        assertEquals(true, res.get("@simple").isValid());
        assertEquals(true, res.get("@simple").isVariable());
        assertEquals("test(test2(test2),test3(test3)),test", res.get("@simple").toString());
    }

}

