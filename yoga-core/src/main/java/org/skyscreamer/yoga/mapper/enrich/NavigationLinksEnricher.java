package org.skyscreamer.yoga.mapper.enrich;

import java.beans.PropertyDescriptor;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;

public class NavigationLinksEnricher implements Enricher
{

   private HrefEnricher hrefEnricher = new HrefEnricher();

   public void setHrefEnricher(HrefEnricher hrefEnricher)
   {
      this.hrefEnricher = hrefEnricher;
   }

   @Override
   public void enrich(Object instance, Selector fieldSelector, HierarchicalModel model,
         Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator)
   {
      HierarchicalModel navigationLinks = model.createChild("navigationLinks", "useless parameter");
      for (PropertyDescriptor property : PropertyUtil.getReadableProperties(instanceType))
      {
         if (!fieldSelector.containsField(property, populator))
         {
            HierarchicalModel propertyLink = navigationLinks.createChild(property,
                  "another useless parameter");
            propertyLink.addSimple("name", property.getName());
            String hrefSuffixAndSelector = hrefSuffix + "?selector=:(" + property.getName() + ")";
            hrefEnricher.enrich(instance, fieldSelector, propertyLink, instanceType,
                  hrefSuffixAndSelector, populator);
         }
      }
   }

}
