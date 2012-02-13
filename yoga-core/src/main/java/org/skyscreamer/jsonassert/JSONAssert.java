package org.skyscreamer.jsonassert;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static org.skyscreamer.jsonassert.JSONCompareMode.*;

/**
 * <p>A set of assertion methods useful for writing tests methods that return JSON.</p>
 *
 * <p>There are two modes, strict and non-strict.  In most cases, you will probably want
 * to set strict to <i>false</i>, since that will make the tests less brittle.</p>
 *
 * <p>Strict tests require all of the elements requested to be returned, and only those elements
 * (ie, the tests are non-extensible).  Arrays of elements must be returned in the same
 * order as expected.  For example, say I'm expecting:</p>
 *
 * <code>{id:123,things['a','b','c']}</code>
 *
 * <p>The following would match when doing non-strict checking, but would fail on strict checking:</p>
 *
 * <code>{id:123,things['c','b','a'],anotherfield:'blah'}</code>
 *
 * <p><i>This library uses org.json.  It has fewer dependencies than other JSON libraries (like net.sf.json),
 * making JSONassert more portable.</i></p>
 */
public class JSONAssert {
    /**
     * Asserts that the JSONObject provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONObject to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
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

    /**
     * Asserts that the JSONArray provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONArray to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
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

    /**
     * Asserts that the JSONArray provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, String actualStr, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expectedStr, actualStr, strict ? STRICT : LENIENT);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONObject provided matches the expected JSONObject.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONObject
     * @param actual JSONObject to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(JSONObject expected, JSONObject actual, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, strict ? STRICT : LENIENT);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONArray provided matches the expected JSONArray.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONArray
     * @param actual JSONArray to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(JSONArray expected, JSONArray actual, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, strict ? STRICT : LENIENT);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }
}
