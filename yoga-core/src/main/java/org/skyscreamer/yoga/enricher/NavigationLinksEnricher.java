package org.skyscreamer.yoga.enricher;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.selector.CoreSelector;
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
        if ( !(selector instanceof CoreSelector) )
        {
            return;
        }

        Class<?> instanceType = event.getValueType();
        Object instance = event.getValue();

        HttpServletResponse response = event.getRequestContext().getResponse();
        String urlSuffix = event.getRequestContext().getUrlSuffix();

        HierarchicalModel<?> navigationLinks = event.getModel().createChildMap( "navigationLinks" );
        Set<String> fieldNames = new TreeSet<String>( selector.getAllPossibleFields( instanceType ) );
        fieldNames.removeAll( selector.getSelectedFieldNames( instanceType ) );

        for (String fieldName : fieldNames)
        {
            HierarchicalModel<?> navModel = navigationLinks.createChildMap( fieldName );
            String hrefSuffixAndSelector = String.format( "%s?selector=:(%s)", urlSuffix, fieldName );
            hrefEnricher.addUrl( instance, instanceType, hrefSuffixAndSelector, navModel, response );
            navModel.addProperty( "name", fieldName );
        }
    }
}
