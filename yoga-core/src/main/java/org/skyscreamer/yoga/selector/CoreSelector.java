package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;

public class CoreSelector implements Selector
{
   @Override
   public Selector getField(PropertyDescriptor property)
   {
      return this;
   }

   @Override
   public boolean containsField(PropertyDescriptor property)
   {
      System.out.println("~~~" + property.getName());
	   return property.getReadMethod().isAnnotationPresent(Core.class);
   }
}
