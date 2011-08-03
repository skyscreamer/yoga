package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.mapper.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class JsonSelectorView extends AbstractYogaView
{
   protected void render(HttpServletRequest request, HttpServletResponse response,
         Selector selector, Object value) throws IOException
   {
      Object viewData = null;
      if (value instanceof Iterable<?>)
      {
         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         for (Object instance : (Iterable<?>) value)
         {
            list.add(getSingleResult(instance, selector));
         }
         viewData = list;
      }
      else
      {
         viewData = getSingleResult(value, selector);
      }
      getObjectMapper().writeValue(response.getOutputStream(), viewData);
   }

   protected ObjectMapper getObjectMapper()
   {
      return new ObjectMapper();
   }

   protected Map<String, Object> getSingleResult(Object value, Selector selector)
   {
      MapHierarchicalModel model = new MapHierarchicalModel();
      resultTraverser.traverse(value, selector, model);
      return model.getObjectTree();
   }

   @Override
   public String getContentType()
   {
      return "application/json";
   }
}
