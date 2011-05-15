package org.skyscreamer.yoga.selector;

import java.lang.reflect.AccessibleObject;
import java.util.HashMap;
import java.util.Map;

public class DefinedSelectorImpl implements Selector
{
   Map<String, DefinedSelectorImpl> _fields = new HashMap<String, DefinedSelectorImpl>();

   public Map<String, DefinedSelectorImpl> getFields()
   {
      return _fields;
   }

   public DefinedSelectorImpl getField(String field)
   {
      if (_fields.containsKey(field))
         return _fields.get(field);
      else
         return null;
   }

   public boolean containsField(String field, AccessibleObject accessibleObject)
   {
      return _fields.containsKey(field);
   }

   @Override
   public String toString()
   {
      return _fields.toString();
   }
}
