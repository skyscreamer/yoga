package org.skyscreamer.yoga.mapper.enrich;

import java.beans.PropertyDescriptor;

import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;

public class NavigationLinksEnricher implements Enricher
{

   private HrefEnricher hrefEnricher = new HrefEnricher();

   public void setHrefEnricher(HrefEnricher hrefEnricher)
   {
      this.hrefEnricher = hrefEnricher;
   }

   @Override
   public void enrich(HttpServletResponse response, Object instance, Selector fieldSelector,
         HierarchicalModel model, Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator)
   {
      if (!(fieldSelector instanceof CoreSelector))
      {
         return;
      }

      HierarchicalModel navigationLinks = model
            .createChild( "navigationLinks", "useless parameter" );
      for (PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ))
      {
         if (!fieldSelector.containsField( property, populator ))
         {
            HierarchicalModel propertyLink = navigationLinks.createChild( property,
                  "another useless parameter" );
            propertyLink.addSimple( "name", property.getName() );
            String hrefSuffixAndSelector = hrefSuffix + "?selector=:(" + property.getName() + ")";
            hrefEnricher.enrich( response, instance, fieldSelector, propertyLink,
                  instanceType, hrefSuffixAndSelector, populator );
         }
      }
   }

}
