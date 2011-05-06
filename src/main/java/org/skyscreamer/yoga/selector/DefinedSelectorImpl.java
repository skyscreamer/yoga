package org.skyscreamer.yoga.selector;

import java.lang.reflect.AccessibleObject;
import java.util.HashMap;
import java.util.Map;

public class DefinedSelectorImpl implements Selector
{
   Map<String, SelectorField> _fields = new HashMap<String, SelectorField>();

   public Map<String, SelectorField> getFields()
   {
      return _fields;
   }

   public DefinedSelectorImpl getField(String field)
   {
      if (_fields.containsKey(field))
         return _fields.get(field).getSelector();
      else
         return null;
   }

   public boolean containsField(String field, AccessibleObject accessibleObject)
   {
      return _fields.containsKey(field);
   }
}
