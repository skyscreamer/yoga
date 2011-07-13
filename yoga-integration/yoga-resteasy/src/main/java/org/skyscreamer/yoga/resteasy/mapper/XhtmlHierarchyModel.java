package org.skyscreamer.yoga.resteasy.mapper;

import java.beans.PropertyDescriptor;

import org.dom4j.Element;
import org.skyscreamer.yoga.resteasy.annotations.Nested;
import org.skyscreamer.yoga.resteasy.util.NameUtil;
import org.skyscreamer.yoga.mapper.HierarchicalModel;

public class XhtmlHierarchyModel implements HierarchicalModel
{
   Element element;
   String childName = null;
   Element a = null;

   public XhtmlHierarchyModel(Element element)
   {
      this.element = element;
   }

   public XhtmlHierarchyModel(Element element, String childName)
   {
      super();
      this.element = element;
      this.childName = childName;
   }

   @Override
   public void addSimple(PropertyDescriptor property, Object result)
   {
      addSimple(property.getName(), result);
   }

   @Override
   public void addSimple(String name, Object result)
   {
      String elementName = childName == null ? name : childName;
      if (elementName.equals("href"))
      {
         a = element.addElement("a");
         a.addAttribute("href", result.toString());
      }
      else if (a != null)
      {
         a.setText(result.toString());
      }
      else
      {
         element.addElement("span").addAttribute("class", elementName).setText(result.toString());
      }   
   }

   @Override
   public HierarchicalModel createChild(PropertyDescriptor property, Object result)
   {
      return new XhtmlHierarchyModel(element.addElement("div").addAttribute("class", property.getName()));
   }

   @Override
   public HierarchicalModel createList(PropertyDescriptor property, Object result)
   {
      Element div = element.addElement("div").addAttribute("class", property.getName());
      Nested nested = property.getReadMethod().getAnnotation(Nested.class);
      if (nested != null)
      {
         return new XhtmlHierarchyModel(div, nested.childName());
      }
      else
      {
         String name = NameUtil.getName(result.getClass());
         return new XhtmlHierarchyModel(div, name);
      }
   }

}
