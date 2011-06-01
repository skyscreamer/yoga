package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
   public Selector getField(PropertyDescriptor propertyDescriptor)
   {
      List<Selector> children = new ArrayList<Selector>();
      for (Selector s : selectors)
      {
         children.add(s.getField(propertyDescriptor));
      }
      return new CombinedSelector(children);
   }

   @Override
   public boolean containsField(PropertyDescriptor property)
   {
      for (Selector selector : selectors)
      {
         if (selector.containsField(property))
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
