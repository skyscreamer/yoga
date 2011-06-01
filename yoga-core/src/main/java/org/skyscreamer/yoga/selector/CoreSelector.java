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
      return property.getReadMethod().isAnnotationPresent(Core.class);
   }
}
