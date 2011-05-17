package org.skyscreamer.yoga.traverser;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;


public class ListHierarchicalModel extends AbstractHierarchicalModel
{

   List<Object> list = new ArrayList<Object>();
   
   @Override
   public void addSimple(String field, AccessibleObject getter, Object result)
   {
      list.add(result);
   }

   public List<Object> getList()
   {
      return list;
   }

}
