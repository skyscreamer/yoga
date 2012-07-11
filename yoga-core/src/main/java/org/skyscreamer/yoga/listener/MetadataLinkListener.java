package org.skyscreamer.yoga.listener;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.model.MapHierarchicalModel;

public class MetadataLinkListener implements RenderingListener
{
    private MetaDataService metaDataService;

    public void setMetaDataService( MetaDataService metaDataService )
    {
        this.metaDataService = metaDataService;
    }

    @Override
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD || event.getSelector().isInfluencedExternally())
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
