package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.model.HierarchicalModel;
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

        HierarchicalModel metaDataLink = entityContext.getModel().createChild( "metadata" );
        metaDataLink.addSimple( "href",
                metaDataService.getHref( entityContext.getInstanceType(), entityContext.getRequestContext().getUrlSuffix() ) );
    }

}
