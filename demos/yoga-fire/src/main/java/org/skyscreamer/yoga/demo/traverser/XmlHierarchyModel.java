package org.skyscreamer.yoga.demo.traverser;

import java.lang.reflect.AccessibleObject;

import org.dom4j.Element;
import org.skyscreamer.yoga.demo.annotations.Attribute;
import org.skyscreamer.yoga.demo.annotations.Nested;
import org.skyscreamer.yoga.traverser.HierarchicalModel;

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
   public void addSimple(String field, AccessibleObject getter, Object result)
   {
      String elementName = childName == null ? field : childName;
      if (elementName.equals("href") || getter.isAnnotationPresent(Attribute.class))
      {
         element.addAttribute(elementName, result.toString());
      }
      else
      {
         element.addElement(elementName).setText(result.toString());
      }
   }

   @Override
   public HierarchicalModel createChild(String field, AccessibleObject getter, Object result)
   {
      return new XmlHierarchyModel(element.addElement(field));
   }

   @Override
   public HierarchicalModel createList(String field, AccessibleObject getter, Object result)
   {
      Nested nested = getter.getAnnotation(Nested.class);
      if (nested != null)
         return new XmlHierarchyModel(element.addElement(field), nested.childName());
      else
         return this;
   }

}
