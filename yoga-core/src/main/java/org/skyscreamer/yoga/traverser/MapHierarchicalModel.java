package org.skyscreamer.yoga.traverser;

import java.lang.reflect.AccessibleObject;
import java.util.HashMap;


public class MapHierarchicalModel extends AbstractHierarchicalModel
{
   HashMap<String, Object> objectTree = new HashMap<String, Object>();

   @Override
   public void addSimple(String field, AccessibleObject getter, Object result)
   {
      objectTree.put(field, result);
   }

   public HashMap<String, Object> getObjectTree()
   {
      return objectTree;
   }

}
