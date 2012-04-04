package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;


public abstract class AbstractHierarchicalModel implements HierarchicalModel
{
   @Override
   public HierarchicalModel createChild(PropertyDescriptor property)
   {
      MapHierarchicalModel child = new MapHierarchicalModel();
      addSimple(property, child.getObjectTree());
      return child;
   }

   @Override
   public HierarchicalModel createChild(String property)
   {
      MapHierarchicalModel child = new MapHierarchicalModel();
      addSimple(property, child.getObjectTree());
      return child;
   }

   @Override
   public HierarchicalModel createList(PropertyDescriptor property)
   {
      ListHierarchicalModel child = new ListHierarchicalModel();
      addSimple(property, child.getList());
      return child;
   }
   
   @Override
   public HierarchicalModel createList(String property)
   {
      ListHierarchicalModel child = new ListHierarchicalModel();
      addSimple(property, child.getList());
      return child;
   }
}
