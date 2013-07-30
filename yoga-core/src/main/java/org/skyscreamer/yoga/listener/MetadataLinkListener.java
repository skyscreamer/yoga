package org.skyscreamer.yoga.listener;

import java.io.IOException;

import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.model.MapHierarchicalModel;

public class MetadataLinkListener implements RenderingListener
{
    private MetaDataRegistry _metaDataRegistry;

    public MetadataLinkListener()
    {
    }

    public MetadataLinkListener( MetaDataRegistry metaDataRegistry )
    {
        this._metaDataRegistry = metaDataRegistry;
    }

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
           MapHierarchicalModel<?> mapHierarchicalModel = (MapHierarchicalModel<?>) event.getModel();
		   MapHierarchicalModel<?> childMap = mapHierarchicalModel.createChildMap( "metadata" );
		   childMap.addProperty( "href", url );
		   childMap.finished();
        }
    }

}
