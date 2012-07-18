package org.skyscreamer.yoga.listener;

import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.model.MapHierarchicalModel;

public class MetadataLinkListener implements RenderingListener
{
    private MetaDataRegistry _metaDataRegistry;

    public void setMetaDataRegistry( MetaDataRegistry metaDataRegistry )
    {
        this._metaDataRegistry = metaDataRegistry;
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

        String url = _metaDataRegistry.getMetadataHref( type, urlSuffix );

        if (url != null)
        {
           ( (MapHierarchicalModel<?>) event.getModel() ).createChildMap( "metadata" ).addProperty( "href", url );
        }
    }

}
