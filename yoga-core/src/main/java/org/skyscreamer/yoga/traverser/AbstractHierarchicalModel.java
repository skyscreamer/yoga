package org.skyscreamer.yoga.traverser;

import java.lang.reflect.AccessibleObject;


public abstract class AbstractHierarchicalModel implements HierarchicalModel
{
   @Override
   public HierarchicalModel createChild(String field, AccessibleObject getter, Object result)
   {
      MapHierarchicalModel child = new MapHierarchicalModel();
      addSimple(field, getter, child.getObjectTree());
      return child;
   }

   @Override
   public HierarchicalModel createList(String field, AccessibleObject getter, Object result)
   {
      ListHierarchicalModel child = new ListHierarchicalModel();
      addSimple(field, getter, child.getList());
      return child;
   }
}
