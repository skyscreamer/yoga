package org.skyscreamer.jsonassert;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 2/4/12
 * Time: 12:14 PM
 */
public class JSONCompare {
    // Entry for JSONassert's unit tests
    public static JSONCompareResult compareJSON(String expectedStr, String actualStr, boolean strict)
            throws JSONException
    {
        Object expected = JSONParser.parseJSON(expectedStr);
        Object actual = JSONParser.parseJSON(actualStr);
        if ((expected instanceof JSONObject) && (actual instanceof JSONObject)) {
            return compareJSON((JSONObject) expected, (JSONObject) actual, strict);
        }
        else if ((expected instanceof JSONArray) && (actual instanceof JSONArray)) {
            return compareJSON((JSONArray)expected, (JSONArray)actual, strict);
        }
        else if (expected instanceof JSONObject) {
            throw new IllegalArgumentException("Expected a JSON object, but passed in a JSON array.");
        }
        else {
            throw new IllegalArgumentException("Expected a JSON array, but passed in a JSON object.");
        }
    }

    public static JSONCompareResult compareJSON(JSONObject expected, JSONObject actual, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = new JSONCompareResult();
        boolean extensible = !strict;
        boolean strictOrder = strict;
        compareJSON("", expected, actual, extensible, strictOrder, result);
        return result;
    }

    public static JSONCompareResult compareJSON(JSONArray expected, JSONArray actual, boolean strict)
            throws JSONException
    {
        JSONCompareResult result = new JSONCompareResult();
        boolean extensible = !strict;
        boolean strictOrder = strict;
        compareJSONArray("", expected, actual, extensible, strictOrder, result);
        return result;
    }

    private static void compareJSON(String prefix, JSONObject expected, JSONObject actual, boolean extensible, boolean strictOrder, JSONCompareResult result)
            throws JSONException
    {
        // Check that actual contains all the expected values
        Set<String> expectedKeys = getKeys(expected);
        for(String key : expectedKeys) {
            Object expectedValue = expected.get(key);
            if (actual.has(key)) {
                Object actualValue = actual.get(key);
                String fullKey = prefix + key;
                compareValues(fullKey, expectedValue, actualValue, extensible, strictOrder, result);
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

    private static void compareValues(String fullKey, Object expectedValue, Object actualValue, boolean extensible, boolean strictOrder, JSONCompareResult result) throws JSONException {
        if (expectedValue.getClass().isAssignableFrom(actualValue.getClass())) {
            if (expectedValue instanceof JSONArray) {
                compareJSONArray(fullKey , (JSONArray)expectedValue, (JSONArray)actualValue, extensible, strictOrder, result);
            }
            else if (expectedValue instanceof JSONObject) {
                compareJSON(fullKey + ".", (JSONObject) expectedValue, (JSONObject) actualValue, extensible, strictOrder, result);
            }
            else if (!expectedValue.equals(actualValue)) {
                result.fail(fullKey, expectedValue, actualValue);
            }
        }
    }

    private static void compareJSONArray(String key, JSONArray expected, JSONArray actual, boolean extensible,
                                         boolean strictOrder, JSONCompareResult result) throws JSONException
    {
        if (expected.length() != actual.length()) {
            result.fail(key + "[]: Expected " + expected.length() + " values and got " + actual.length());
            return;
        }
        else if (expected.length() == 0) {
            return; // Nothing to compare
        }

        if (strictOrder) {
            for(int i = 0 ; i < expected.length() ; ++i) {
                Object expectedValue = expected.get(i);
                Object actualValue = actual.get(i);
                compareValues(key + "[" + i + "]", expectedValue, actualValue, extensible, strictOrder, result);
            }
        }
        else if (allSimpleValues(expected)) {
            Map<Object, Integer> expectedCount = CollectionUtils.getCardinalityMap(jsonArrayToList(expected));
            Map<Object, Integer> actualCount = CollectionUtils.getCardinalityMap(jsonArrayToList(actual));
            for(Object o : expectedCount.keySet()) {
                if (!actualCount.containsKey(o)) {
                    result.fail(key + "[]: Expected " + o + ", but not found");
                }
                else if (actualCount.get(o) != expectedCount.get(o)) {
                    result.fail(key + "[]: Expected contains " + expectedCount.get(o) + " " + o
                            + " actual contains " + actualCount.get(o));
                }
            }
            for(Object o : actualCount.keySet()) {
                if (!expectedCount.containsKey(o)) {
                    result.fail(key + "[]: Contains " + o + ", but not expected");
                }
            }
        }
        else if (allJSONObjects(expected)) {
            String uniqueKey = findUniqueKey(expected);
            if (uniqueKey == null) {
                throw new IllegalArgumentException("Non-strict checking of an array of objects without a common simple " +
                        "field is not supported.  ([{id:1,a:3},{id:2,b:4}] is ok, [{dog:\"fido\"},{cat:\"fluffy\"}] is not)");
            }
            Map<Object, JSONObject> expectedValueMap = arrayOfJsonObjectToMap(expected, uniqueKey);
            Map<Object, JSONObject> actualValueMap = arrayOfJsonObjectToMap(actual, uniqueKey);
            for(Object id : expectedValueMap.keySet()) {
                if (!actualValueMap.containsKey(id)) {
                    result.fail(key + "[]: Expected but did not find object where " + uniqueKey + "=" + id);
                    continue;
                }
                JSONObject expectedValue = expectedValueMap.get(id);
                JSONObject actualValue = actualValueMap.get(id);
                compareValues(key + "[" + uniqueKey + "=" + id + "]", expectedValue, actualValue, extensible, strictOrder, result);
            }
            for(Object id : actualValueMap.keySet()) {
                if (!expectedValueMap.containsKey(id)) {
                    result.fail(key + "[]: Contains object where \" + uniqueKey + \"=\" + id + \", but not expected");
                }
            }
        }
        else if (allJSONArrays(expected)) {
            throw new IllegalArgumentException("Non-strict checking of arrays of arrays (e.g. [[1,2],[3,4]]) is not supported.");
        }
        else {
            throw new IllegalArgumentException("A mixture of simple values, objects, and arrays (e.g. [1,2,{a:\"b\"},[]]) " +
                    "are not supported in non-strict mode.");
        }
    }

    private static Map<Object,JSONObject> arrayOfJsonObjectToMap(JSONArray array, String uniqueKey) throws JSONException {
        Map<Object, JSONObject> valueMap = new HashMap<Object, JSONObject>();
        for(int i = 0 ; i < array.length() ; ++i) {
            JSONObject jsonObject = (JSONObject)array.get(i);
            Object id = jsonObject.get(uniqueKey);
            valueMap.put(id, jsonObject);
        }
        return valueMap;
    }

    private static String findUniqueKey(JSONArray expected) throws JSONException {
        // Find a unique key for the object (id, name, whatever)
        JSONObject o = (JSONObject)expected.get(0); // There's at least one at this point
        for(String candidate : getKeys(o)) {
            Object candidateValue = o.get(candidate);
            if (isSimpleValue(candidateValue)) {
                Set<Object> seenValues = new HashSet<Object>();
                seenValues.add(candidateValue);
                boolean isUsableKey = true;
                for(int i = 1 ; i < expected.length() ; ++i) {
                    JSONObject other = (JSONObject)expected.get(i);
                    if (!other.has(candidate)) {
                        isUsableKey = false;
                        break;
                    }
                    Object comparisonValue = other.get(candidate);
                    if (!isSimpleValue(comparisonValue) || seenValues.contains(comparisonValue)) {
                        isUsableKey = false;
                        break;
                    }
                    seenValues.add(comparisonValue);
                }
                if (isUsableKey) {
                    return candidate;
                }
            }
        }
        // No usable unique key :-(
        return null;
    }

    private static List<Object> jsonArrayToList(JSONArray expected) throws JSONException {
        List<Object> jsonObjects = new ArrayList<Object>(expected.length());
        for(int i = 0 ; i < expected.length() ; ++i) {
            jsonObjects.add(expected.get(i));
        }
        return jsonObjects;
    }

    private static boolean allSimpleValues(JSONArray array) throws JSONException {
        for(int i = 0 ; i < array.length() ; ++i) {
            if (!isSimpleValue(array.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSimpleValue(Object o) {
        return !(o instanceof JSONObject) && !(o instanceof JSONArray);
    }

    private static boolean allJSONObjects(JSONArray array) throws JSONException {
        for(int i = 0 ; i < array.length() ; ++i) {
            if (!(array.get(i) instanceof JSONObject)) {
                return false;
            }
        }
        return true;
    }

    private static boolean allJSONArrays(JSONArray array) throws JSONException {
        for(int i = 0 ; i < array.length() ; ++i) {
            if (!(array.get(i) instanceof JSONArray)) {
                return false;
            }
        }
        return true;
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
