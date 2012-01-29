package org.skyscreamer.jsonassert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 1/29/12
 * Time: 2:43 PM
 *
 * Known issues:
 *  + Unless the order is strict checking does not handle mixed types in the JSONArray
 *    (e.g. [1,2,{a:"b"}] or [{pet:"cat"},{car:"Ford"}])
 */
public class JSONAssert {
    public static void assertEquals(String expectedStr, String actualStr, boolean strict)
            throws JSONException
    {
        JSONAssertResult result = compareJSON(expectedStr, actualStr, strict);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    protected static JSONAssertResult compareJSON(String expectedStr, String actualStr, boolean strict)
             throws JSONException
    {
        JSONObject expected = new JSONObject(expectedStr);
        JSONObject actual = new JSONObject(actualStr);
        JSONAssertResult result = new JSONAssertResult();
        boolean extensible = !strict;
        boolean strictOrder = strict;
        compareJSON("", expected, actual, extensible, strictOrder, result);
        return result;
    }

    private static void compareJSON(String prefix, JSONObject expected, JSONObject actual, boolean extensible, boolean strictOrder, JSONAssertResult result)
            throws JSONException
    {
        // Check that actual contains all the expected values
        Set<String> expectedKeys = getKeys(expected);
        for(String key : expectedKeys) {
            Object expectedValue = expected.get(key);
            if (actual.has(key)) {
                Object actualValue = actual.get(key);
                if (expectedValue.getClass().isAssignableFrom(actualValue.getClass())) {
                    if (expectedValue instanceof JSONArray) {
                        compareJSONArray(prefix + key, (JSONArray)expectedValue, (JSONArray)actualValue, extensible, strictOrder, result);
                    }
                    else if (expectedValue instanceof JSONObject) {
                        compareJSON(prefix + key + ".", (JSONObject) expectedValue, (JSONObject) actualValue, extensible, strictOrder, result);
                    }
                    else if (!expectedValue.equals(actualValue)) {
                        result.fail(prefix + key, expectedValue, actualValue);
                    }
                }
            }
            else {
                result.fail("Does not contain expected key: " + prefix + key);
            }
        }
        
        // If strict, check for vice-versa
        if (!extensible) {
            Set<String> actualKeys = getKeys(actual);
            for(String key : actualKeys) {
                if (!expected.has(key)) {
                    result.fail("Strict checking failed.  Got but did not expect: " + prefix + key);
                }
            }
        }
    }

    private static void compareJSONArray(String key, JSONArray expectedValue, JSONArray actualValue, boolean extensible,
                                         boolean strictOrder, JSONAssertResult result)
    {
        // TODO: This is where it gets tricky
    }

    private static Set<String> getKeys(JSONObject jsonObject) {
        Set<String> keys = new TreeSet<String>();
        Iterator<?> iter = jsonObject.keys();
        while(iter.hasNext()) {
            keys.add((String)iter.next());
        }
        return keys;
    }
}
