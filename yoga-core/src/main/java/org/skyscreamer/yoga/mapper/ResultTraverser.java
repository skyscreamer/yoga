package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA. User: corby Date: 4/21/11 Time: 3:07 PM
 */
public class ResultTraverser
{
   public void traverse(Object instance, Selector fieldSelector, HierarchicalModel model)
   {
      PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(getClass(instance));

      for (PropertyDescriptor property : properties)
      {
         String field = property.getName();
         try
         {
            if (fieldSelector.containsField(property))
            {
               Object value = PropertyUtils.getNestedProperty(instance, property.getName());

               Class<?> propertyType = property.getPropertyType();
               if (isNotBean(propertyType))
               {
                  model.addSimple(property, value);
               }
               else if (Iterable.class.isAssignableFrom(propertyType))
               {
                  traverseIterable(fieldSelector, model, property, (Iterable<?>) value);
               }
               else
               {
                  traverseChild(fieldSelector, model, property, field, value);
               }
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      }
   }

   protected void traverseIterable(Selector fieldSelector, HierarchicalModel model,
         PropertyDescriptor property, Iterable<?> list)
   {
      HierarchicalModel listModel = model.createList(property, list);
      for (Object o : list)
      {
         if (isNotBean(getClass(o)))
         {
            listModel.addSimple(property, list);
         }
         else
         {
            traverseChild(fieldSelector, listModel, property, property.getName(), o);
         }
      }
   }

   // allow this to be overridden
   protected void traverseChild(Selector parentSelector, HierarchicalModel parent,
         PropertyDescriptor property, String name, Object value)
   {
      traverse(value, parentSelector.getField(property), parent.createChild(property, value));
   }

   public Class<? extends Object> getClass(Object instance)
   {
      return instance.getClass();
   }

   protected boolean isNotBean(Class<?> clazz)
   {
      return clazz.isPrimitive() || clazz.isEnum() || Number.class.isAssignableFrom(clazz)
            || String.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)
            || Character.class.isAssignableFrom(clazz);
   }
}
