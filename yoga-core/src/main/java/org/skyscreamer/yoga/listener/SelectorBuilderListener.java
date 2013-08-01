package org.skyscreamer.yoga.listener;

import java.io.IOException;

import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.model.MapHierarchicalModel;

/**
 * User: cpage
 * Date: 12/10/11 Time: 3:59 PM
 */
public class SelectorBuilderListener extends HrefListener
{
    public static final String FIELD_NAME = "selectorBuilder";

    private UriGenerator _uriGenerator;

    private String suffix = "yoga";

    public SelectorBuilderListener()
    {
        this._uriGenerator = new UriGenerator();
    }

    public SelectorBuilderListener( UriGenerator uriGenerator )
    {
        this._uriGenerator = uriGenerator;
    }

    @Deprecated
    /** use setUriGenerator instead */
    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry)
    {
        this._uriGenerator.setEntityConfigurationRegistry( entityConfigurationRegistry );
    }

    public void setUriGenerator( UriGenerator uriGenerator )
    {
        this._uriGenerator = uriGenerator;
    }

    @Override
    public <T> void eventOccurred( RenderingEvent<T> event ) throws IOException
    {
        if (event.getType() == RenderingEventType.POJO_CHILD && !event.getSelector().isInfluencedExternally())
        {
            String href = _uriGenerator.determineTemplate( event.getValueType() );

            if ( href != null )
            {
                MapHierarchicalModel<?> model = ( MapHierarchicalModel<?> ) event.getModel();
                model.addProperty( FIELD_NAME, _uriGenerator.getUrl( href, suffix, event ) );
            }
        }
    }

    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }
}
