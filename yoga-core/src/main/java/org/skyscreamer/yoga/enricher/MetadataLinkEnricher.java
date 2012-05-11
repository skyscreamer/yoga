package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.selector.CoreSelector;

public class MetadataLinkEnricher implements Enricher
{
    private MetaDataService metaDataService;

    public void setMetaDataService( MetaDataService metaDataService )
    {
        this.metaDataService = metaDataService;
    }

    @Override
    public void enrich( RenderingEvent event )
    {
        if ( !(event.getSelector() instanceof CoreSelector) )
        {
            return;
        }

        Class<?> type = event.getValueType();
        String urlSuffix = event.getRequestContext().getUrlSuffix();
        
        String url = metaDataService.getMetadataHref( type, urlSuffix );

        if(url != null)
        {
            event.getModel().createChildMap( "metadata" ).addProperty( "href", url );
        }
    }

}
