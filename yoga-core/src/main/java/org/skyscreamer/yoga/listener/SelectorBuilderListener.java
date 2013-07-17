package org.skyscreamer.yoga.listener;

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
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD || event.getSelector().isInfluencedExternally())
        {
            return;
        }
        String href = determineTemplate( event.getValueType() );

        if ( href != null )
        {
            href += "." + suffix;
            String url = getUrl( href, event.getValue(), event.getValueType(), event.getRequestContext().getResponse() );
            ( ( MapHierarchicalModel<?> ) event.getModel() ).addProperty( FIELD_NAME, url );
        }
    }

    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }
}
