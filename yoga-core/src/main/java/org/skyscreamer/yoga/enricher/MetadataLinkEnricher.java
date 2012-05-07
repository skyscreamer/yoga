package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.selector.CoreSelector;

public class MetadataLinkEnricher implements Enricher
{
    private MetaDataService metaDataService;

    public void setMetaDataService(MetaDataService metaDataService)
    {
        this.metaDataService = metaDataService;
    }

    @Override
    public void enrich(YogaInstanceContext<?> entityContext)
    {
        if (!(entityContext.getFieldSelector() instanceof CoreSelector))
        {
            return;
        }

        Class<?> type = entityContext.getInstanceType();
        String urlSuffix = entityContext.getRequestContext().getUrlSuffix();
        
        String url = metaDataService.getMetadataHref( type, urlSuffix );

        entityContext.getModel().createChild( "metadata" ).addSimple( "href", url );
    }

}
