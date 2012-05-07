package org.skyscreamer.yoga.enricher;

import java.beans.PropertyDescriptor;

import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.model.HierarchicalModel;
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
    public void enrich(YogaInstanceContext<?> entityContext)
    {
        Selector fieldSelector = entityContext.getFieldSelector();
        if (!(fieldSelector instanceof CoreSelector))
        {
            return;
        }

        Class<?> instanceType = entityContext.getInstanceType();
        YogaRequestContext requestContext = entityContext.getRequestContext();
        FieldPopulator populator = entityContext.getPopulator();

        HierarchicalModel navigationLinks = entityContext.getModel().createChild( "navigationLinks" );
        for (PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ))
        {
            if (fieldSelector.containsField( property, populator ))
                continue;

            HierarchicalModel navModel = navigationLinks.createChild( property.getName() );
            navModel.addSimple( "name", property.getName() );
            String hrefSuffixAndSelector = requestContext.getUrlSuffix() + "?selector=:("
                    + property.getName() + ")";
            YogaInstanceContext<?> clone = entityContext.clone();
            YogaRequestContext childRequestContext = new YogaRequestContext( hrefSuffixAndSelector,
                    requestContext.getRequest(), requestContext.getResponse() );
            clone.setRequestContext( childRequestContext );
            clone.setModel( navModel );
            hrefEnricher.enrich( clone );
        }
    }

}
