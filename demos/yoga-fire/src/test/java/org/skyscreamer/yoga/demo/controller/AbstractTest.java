package org.skyscreamer.yoga.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.skyscreamer.yoga.demo.RunServer;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/19/11 Time: 6:18 PM
 */
public abstract class AbstractTest
{

   static RunServer instance;

   @BeforeClass
   public static void startServer() throws Exception
   {
      if (instance == null)
      {
         instance = new RunServer(8082);
         instance.run(false);
      }
   }

   protected JSONObject getJSONObject(String url, Map<String, String> params) throws Exception
   {
      return new JSONObject(getContent(url, params));
   }

   protected JSONArray getJSONArray(String url, Map<String, String> params) throws Exception
   {
      String content = getContent(url, params);
      return new JSONArray(content);
   }

   private String getContent(String url, Map<String, String> params) throws Exception,
         UnsupportedEncodingException
   {
      RestTemplate restTemplate = new RestTemplate();
      StringBuilder sb = new StringBuilder("http://localhost:8082").append(url).append(".json");
      addParams(params, sb);
      return restTemplate.getForObject(sb.toString(), String.class);
   }

   private void addParams(Map<String, String> params, StringBuilder sb)
   {
      if (params == null)
         return;

      String append = "?";
      for (Entry<String, String> entry : params.entrySet())
      {
         sb.append(append).append(entry.getKey()).append("=").append(entry.getValue());
      }
   }
}
