package org.skyscreamer.yoga.traverser;

import java.beans.PropertyDescriptor;


public abstract class AbstractHierarchicalModel implements HierarchicalModel
{
   @Override
   public HierarchicalModel createChild(PropertyDescriptor property, Object result)
   {
      MapHierarchicalModel child = new MapHierarchicalModel();
      addSimple(property, child.getObjectTree());
      return child;
   }

   @Override
   public HierarchicalModel createList(PropertyDescriptor property, Object result)
   {
      ListHierarchicalModel child = new ListHierarchicalModel();
      addSimple(property, child.getList());
      return child;
   }
}
