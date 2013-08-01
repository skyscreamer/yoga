package org.skyscreamer.yoga.listener;

import java.io.IOException;

import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class HrefListener implements RenderingListener
{

    private UriGenerator _uriGenerator;

    public HrefListener()
    {
        this( new UriGenerator() );
    }

    @Deprecated
    /** use the UriGenerator constructor instead */
    public HrefListener( EntityConfigurationRegistry _entityConfigurationRegistry)
    {
        this( new UriGenerator( _entityConfigurationRegistry ) );
    }

    public HrefListener( UriGenerator uriGenerator )
    {
        setUriGenerator( uriGenerator );
    }

    public void setUriGenerator( UriGenerator uriGenerator )
    {
        this._uriGenerator = uriGenerator;
    }

    public UriGenerator getUriGenerator()
    {
        return _uriGenerator;
    }

    @Deprecated
    /** use setUriGenerator instead */
    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry)
    {
        this._uriGenerator.setEntityConfigurationRegistry( entityConfigurationRegistry );
    }

    @Override
    public <T> void eventOccurred( RenderingEvent<T> event ) throws IOException
    {
        if (event.getType() == RenderingEventType.POJO_CHILD)
        {
            String url = getUrl( event, event.getRequestContext().getUrlSuffix() );
            if( url != null )
            {
                ((MapHierarchicalModel<?>) event.getModel()).addProperty( SelectorParser.HREF, url );
            }
        }
    }

    public <T> String getUrl( RenderingEvent<T> event, String suffix ) throws IOException
    {
        return this._uriGenerator.getUrl( event, suffix );
    }
}
