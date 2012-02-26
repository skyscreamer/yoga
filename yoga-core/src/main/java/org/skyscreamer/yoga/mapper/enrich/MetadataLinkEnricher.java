package org.skyscreamer.yoga.mapper.enrich;

import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;

public class MetadataLinkEnricher implements Enricher
{
   private MetaDataService metaDataService;

   public void setMetaDataService(MetaDataService metaDataService)
   {
      this.metaDataService = metaDataService;
   }

   @Override
   public void enrich(HttpServletResponse response, Object instance, Selector fieldSelector,
         HierarchicalModel model, Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator)
   {
      if (!(fieldSelector instanceof CoreSelector))
      {
         return;
      }

      HierarchicalModel metaDataLink = model.createChild( "metadata", "useless parameter" );
      metaDataLink.addSimple( "href", metaDataService.getHref( instanceType, hrefSuffix ) );
   }

}
