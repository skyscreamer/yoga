package org.skyscreamer.yoga.demo.traverser;

import java.lang.reflect.AccessibleObject;

import org.dom4j.Element;
import org.skyscreamer.yoga.demo.annotations.Nested;
import org.skyscreamer.yoga.demo.view.NameUtil;
import org.skyscreamer.yoga.traverser.HierarchicalModel;

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
   public void addSimple(String field, AccessibleObject getter, Object result)
   {
      String elementName = childName == null ? field : childName;
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
   public HierarchicalModel createChild(String field, AccessibleObject getter, Object result)
   {
      return new XhtmlHierarchyModel(element.addElement("div").addAttribute("class", field));
   }

   @Override
   public HierarchicalModel createList(String field, AccessibleObject getter, Object result)
   {
      Element div = element.addElement("div").addAttribute("class", field);
      Nested nested = getter.getAnnotation(Nested.class);
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
