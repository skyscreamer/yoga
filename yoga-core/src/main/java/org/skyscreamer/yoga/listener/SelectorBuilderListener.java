package org.skyscreamer.yoga.listener;

import java.io.IOException;

import org.skyscreamer.yoga.model.MapHierarchicalModel;

/**
 * User: cpage
 * Date: 12/10/11 Time: 3:59 PM
 */
public class SelectorBuilderListener extends HrefListener
{
    public static final String FIELD_NAME = "selectorBuilder";

    private String suffix = "yoga";

    @Override
    public <T> void eventOccurred( RenderingEvent<T> event ) throws IOException
    {
        if (event.getType() == RenderingEventType.POJO_CHILD && !event.getSelector().isInfluencedExternally())
        {
            String href = determineTemplate( event.getValueType() );

            if ( href != null )
            {
                MapHierarchicalModel<?> model = ( MapHierarchicalModel<?> ) event.getModel();
                model.addProperty( FIELD_NAME, getUrl( href, suffix, event ) );
            }
        }
    }

    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }
}
