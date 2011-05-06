package org.skyscreamer.yoga.selector;

import java.lang.reflect.AccessibleObject;

public class CoreSelector implements Selector
{

   @Override
   public Selector getField(String field)
   {
      return this;
   }

   @Override
   public boolean containsField(String field, AccessibleObject accessibleObject)
   {
      return accessibleObject.isAnnotationPresent(Core.class);
   }

}
