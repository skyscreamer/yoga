package org.skyscreamer.jsonassert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 2/4/12
 * Time: 11:48 AM
 */
public class JSONParser {
    public static Object parseJSON(String s) throws JSONException {
        if (s.trim().startsWith("{")) {
            return new JSONObject(s);
        }
        else if (s.trim().startsWith("[")) {
            return new JSONArray(s);
        }
        throw new IllegalArgumentException("Unparsable JSON string: " + s);
    }
}
