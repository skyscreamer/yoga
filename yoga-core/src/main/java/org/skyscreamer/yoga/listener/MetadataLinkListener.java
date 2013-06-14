package org.skyscreamer.yoga.listener;

import java.io.IOException;

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
    public <T> void eventOccurred( RenderingEvent<T> event ) throws IOException
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
