package org.skyscreamer.yoga.selector;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CombinedSelector implements Selector
{
   Collection<Selector> selectors = new ArrayList<Selector>();

   public CombinedSelector(Selector... selectors)
   {
      this(Arrays.asList(selectors));
   }

   public CombinedSelector(Iterable<Selector> selectors)
   {
      for (Selector selector : selectors)
      {
         if (selector != null)
         {
            this.selectors.add(selector);
         }
      }
   }

   @Override
   public Selector getField(String field)
   {
      ArrayList<Selector> children = new ArrayList<Selector>();
      for (Selector s : selectors)
      {
         children.add(s.getField(field));
      }
      return new CombinedSelector(children);
   }

   @Override
   public boolean containsField(String field, AccessibleObject accessibleObject)
   {
      for (Selector selector : selectors)
      {
         if (selector.containsField(field, accessibleObject))
         {
            return true;
         }
      }
      return false;
   }

   public Collection<Selector> getChildren()
   {
      return selectors;
   }

}
