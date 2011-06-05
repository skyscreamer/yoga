package org.skyscreamer.yoga.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA. User: corby Date: 4/21/11 Time: 3:07 PM
 */
public class ResultMapper
{
   ResultTraverser _resultTraverser = new ResultTraverser();

   public void setResultTraverser(ResultTraverser resultTraverser )
   {
      this._resultTraverser = resultTraverser;
   }

   public List<HashMap<String, Object>> populate(Iterable<?> instances, Selector fieldSelector)
   {
      List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
      for (Object instance : instances)
      {
         result.add(populate(instance, fieldSelector));
      }
      return result;
   }

   public HashMap<String, Object> populate(Object instance, Selector fieldSelector)
   {
      MapHierarchicalModel model = new MapHierarchicalModel();
      _resultTraverser.traverse(instance, fieldSelector, model);
      return model.getObjectTree();
   }
   
   public ResultTraverser getResultTraverser()
   {
	  return _resultTraverser;
   }
}
