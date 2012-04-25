package org.skyscreamer.yoga.mapper.enrich;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverserContext;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;

import java.beans.PropertyDescriptor;

public class NavigationLinksEnricher implements Enricher
{

    private HrefEnricher hrefEnricher = new HrefEnricher();

    public void setHrefEnricher( HrefEnricher hrefEnricher )
    {
        this.hrefEnricher = hrefEnricher;
    }

    @Override
    public void enrich( Object instance, Selector fieldSelector, HierarchicalModel model,
            Class<?> instanceType, FieldPopulator<?> populator, ResultTraverserContext context )
    {
        if ( !(fieldSelector instanceof CoreSelector) )
        {
            return;
        }

        HierarchicalModel navigationLinks = model
                .createChild( "navigationLinks" );
        for ( PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ) )
        {
            if ( !fieldSelector.containsField( property, populator ) )
            {
                HierarchicalModel propertyLink = navigationLinks.createChild( property );
                propertyLink.addSimple( "name", property.getName() );
                String hrefSuffixAndSelector = context.getHrefSuffix() + "?selector=:(" + property.getName() + ")";
                hrefEnricher.enrich( instance, fieldSelector, propertyLink,
                        instanceType, populator, new ResultTraverserContext( hrefSuffixAndSelector, context.getResponse() ) );
            }
        }
    }

}
