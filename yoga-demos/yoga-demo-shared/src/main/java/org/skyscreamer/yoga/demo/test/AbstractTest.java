package org.skyscreamer.yoga.demo.test;

import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/19/11
 * Time: 6:18 PM
 */
public abstract class AbstractTest {

    protected JSONObject getJSONObject(String url, Map<String, String> params) throws Exception {
        return new JSONObject(getContent(url, params));
    }

    protected JSONArray getJSONArray(String url, Map<String, String> params) throws Exception {
        return new JSONArray(getContent(url, params));
    }

    private String getContent(String url, Map<String, String> params) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder("http://localhost:8082").append(url).append(".json");
        addParams(params, sb);
        return restTemplate.getForObject(sb.toString(), String.class);
    }

    private void addParams(Map<String, String> params, StringBuilder sb) {
        if (params == null)
            return;

        String append = "?";
        for (Entry<String, String> entry : params.entrySet()) {
            sb.append(append).append(entry.getKey()).append("=").append(entry.getValue());
        }
    }
}
