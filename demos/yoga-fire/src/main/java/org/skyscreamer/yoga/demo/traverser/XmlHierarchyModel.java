package org.skyscreamer.yoga.demo.traverser;

import java.beans.PropertyDescriptor;

import org.dom4j.Element;
import org.skyscreamer.yoga.demo.annotations.Attribute;
import org.skyscreamer.yoga.demo.annotations.Nested;
import org.skyscreamer.yoga.mapper.HierarchicalModel;

public class XmlHierarchyModel implements HierarchicalModel
{
   Element element;
   String childName = null;

   public XmlHierarchyModel(Element element)
   {
      this.element = element;
   }

   public XmlHierarchyModel(Element element, String childName)
   {
      super();
      this.element = element;
      this.childName = childName;
   }

   @Override
   public void addSimple(PropertyDescriptor property, Object result)
   {
      addSimple(property.getName(), result, property.getReadMethod().isAnnotationPresent(Attribute.class));
   }

   @Override
   public void addSimple(String name, Object result)
   {
      addSimple(name, result, false);
   }
   
   public void addSimple(String name, Object result, boolean isAttribute)
   {
      String elementName = childName == null ? name : childName;
      if (name.equals("href") || isAttribute)
      {
         element.addAttribute(elementName, result.toString());
      }
      else
      {
         element.addElement(elementName).setText(result.toString());
      }
   }
   
   @Override
   public HierarchicalModel createChild(PropertyDescriptor property, Object result)
   {
      return new XmlHierarchyModel(element.addElement(property.getName()));
   }

   @Override
   public HierarchicalModel createList(PropertyDescriptor property, Object result)
   {
      Nested nested = property.getReadMethod().getAnnotation(Nested.class);
      if (nested != null)
         return new XmlHierarchyModel(element.addElement(property.getName()), nested.childName());
      else
         return this;
   }

}
