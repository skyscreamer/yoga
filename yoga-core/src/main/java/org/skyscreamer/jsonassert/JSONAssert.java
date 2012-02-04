package org.skyscreamer.jsonassert;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 1/29/12
 * Time: 2:43 PM
 *
 * Known issues:
 *  + Unless the order is strict checking does not handle mixed types in the JSONArray
 *    (e.g. [1,2,{a:"b"}] or [{pet:"cat"},{car:"Ford"}])
 *  + Unless the order is strict checking does not arrays of arrays
 *    (e.g. [[1,2],[3,4]])
 *
 *  Decided to stick with org.json for maximum compatibility with various projects.
 *  The implementation is simpler and a little more predictable.  There are also
 *  more maven dependencies in net.sf.json which can cause version conflicts
 *  with some projects.
 */
public class JSONAssert {
    public static void assertEquals(String expectedStr, JSONObject actual, boolean strict)
            throws JSONException
    {
        Object expected = JSONParser.parseJSON(expectedStr);
        if (expected instanceof JSONObject) {
            assertEquals((JSONObject)expected, actual, strict);
        }
        else {
            throw new AssertionError("Expecting a JSON array, but passing in a JSON object");
        }
    }

    public static void assertEquals(String expectedStr, JSONArray actual, boolean strict)
            throws JSONException
    {
        Object expected = JSONParser.parseJSON(expectedStr);
        if (expected instanceof JSONArray) {
            assertEquals((JSONArray)expected, actual, strict);
        }
        else {
            throw new AssertionError("Expecting a JSON object, but passing in a JSON array");
        }
    }

    public static void assertEquals(String expectedStr, String actualStr, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expectedStr, actualStr, strict);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    public static void assertEquals(JSONObject expected, JSONObject actual, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, strict);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    public static void assertEquals(JSONArray expected, JSONArray actual, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, strict);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }
}
