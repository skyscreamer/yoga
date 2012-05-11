package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.model.MapHierarchicalModel;

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
        if (event.getSelector().isInfluencedExternally())
        {
            return;
        }

        Class<?> type = event.getValueType();
        String urlSuffix = event.getRequestContext().getUrlSuffix();

        String url = metaDataService.getMetadataHref( type, urlSuffix );

        if (url != null)
        {
           ( (MapHierarchicalModel<?>) event.getModel() ).createChildMap( "metadata" ).addProperty( "href", url );
        }
    }

}
