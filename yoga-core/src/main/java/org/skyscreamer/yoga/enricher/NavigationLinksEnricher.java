package org.skyscreamer.yoga.enricher;

import java.util.Set;
import java.util.TreeSet;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class NavigationLinksEnricher implements Enricher
{

    private HrefEnricher hrefEnricher = new HrefEnricher();

    public void setHrefEnricher( HrefEnricher hrefEnricher )
    {
        this.hrefEnricher = hrefEnricher;
    }

    @Override
    public void enrich( RenderingEvent event )
    {
        Selector selector = event.getSelector();
        if (selector.isInfluencedExternally())
        {
            return;
        }

        Class<?> instanceType = event.getValueType();
        Object instance = event.getValue();

        String urlSuffix = event.getRequestContext().getUrlSuffix();

        MapHierarchicalModel<?> navigationLinks = ((MapHierarchicalModel<?>) event.getModel())
                .createChildMap( "navigationLinks" );
        Set<String> fieldNames = new TreeSet<String>( selector.getAllPossibleFields( instanceType ) );
        fieldNames.removeAll( selector.getSelectedFieldNames( instanceType ) );

        for (String fieldName : fieldNames)
        {
            MapHierarchicalModel<?> navModel = navigationLinks.createChildMap( fieldName );
            String hrefSuffixAndSelector = String
                    .format( "%s?selector=:(%s)", urlSuffix, fieldName );
            hrefEnricher.addUrl( instance, instanceType, hrefSuffixAndSelector, navModel, event.getRequestContext() );
            navModel.addProperty( "name", fieldName );
        }
    }
}
