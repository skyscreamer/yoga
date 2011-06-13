package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;
import java.util.HashMap;


public class MapHierarchicalModel extends AbstractHierarchicalModel
{
   HashMap<String, Object> objectTree = new HashMap<String, Object>();

   @Override
   public void addSimple(PropertyDescriptor property, Object value)
   {
      objectTree.put(property.getName(), value);
   }

   @Override
   public void addSimple(String name, Object value)
   {
      objectTree.put(name, value);
   }
   
   public HashMap<String, Object> getObjectTree()
   {
      return objectTree;
   }

}
