package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.*;

public class CombinedSelector implements Selector
{
   Collection<Selector> _selectors = new ArrayList<Selector>();

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
            this._selectors.add(selector);
         }
      }
   }

   @Override
   public Selector getField(PropertyDescriptor propertyDescriptor)
   {
      List<Selector> children = new ArrayList<Selector>();
      for (Selector s : _selectors)
      {
         children.add(s.getField(propertyDescriptor));
      }
      return new CombinedSelector(children);
   }

   public Selector getField(String fieldName)
   {
      List<Selector> children = new ArrayList<Selector>();
      for (Selector s : _selectors)
      {
         children.add(s.getField(fieldName));
      }
      return new CombinedSelector(children);
   }

   @Override
   public boolean containsField( PropertyDescriptor property, FieldPopulator<?> fieldPopulator )
   {
      for (Selector selector : _selectors)
      {
         if (selector.containsField(property, fieldPopulator ))
         {
            return true;
         }
      }
      return false;
   }

   @Override
   public boolean containsField(String property)
   {
      for (Selector selector : _selectors)
      {
         if (selector.containsField(property))
         {
            return true;
         }
      }
      return false;   
   }

   public Set<String> getFieldNames()
   {
      Set<String> fieldNames = new HashSet<String>();
      for (Selector selector : _selectors)
      {
         fieldNames.addAll(selector.getFieldNames());
      }
      return fieldNames;
   }

    @Override
    public Map<String, Selector> getFields() {
        Map<String, Selector> fields = new HashMap<String, Selector>();
        for(Selector selector : _selectors)
        {
            fields.putAll(selector.getFields());
        }
        return fields;
    }
}
