package org.skyscreamer.yoga.populator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.traverser.MapHierarchicalModel;
import org.skyscreamer.yoga.traverser.ObjectFieldTraverser;

/**
 * Created by IntelliJ IDEA. User: corby Date: 4/21/11 Time: 3:07 PM
 */
public class ObjectFieldPopulator 
{
   ObjectFieldTraverser objectFieldTraverser = new ObjectFieldTraverser();

   public void setObjectFieldTraverser(ObjectFieldTraverser objectFieldTraverser)
   {
      this.objectFieldTraverser = objectFieldTraverser;
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
      objectFieldTraverser.traverse(instance, fieldSelector, model);
      return model.getObjectTree();
   }
   
   public ObjectFieldTraverser getObjectFieldTraverser() 
   {
	  return objectFieldTraverser;
   }
}
