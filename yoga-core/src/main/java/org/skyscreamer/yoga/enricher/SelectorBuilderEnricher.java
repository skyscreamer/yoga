package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.model.MapHierarchicalModel;

/**
 * Created by IntelliJ IDEA. User: cpage Date: 12/10/11 Time: 3:59 PM
 */
public class SelectorBuilderEnricher extends HrefEnricher implements Enricher
{
    public static final String FIELD_NAME = "selectorBuilder";

    private String suffix = "yoga";

    @Override
    public void enrich( RenderingEvent event )
    {
        if (!event.getSelector().isInfluencedExternally())
        {
            String href = determineTemplate( event.getValueType() );

            if ( href != null )
            {
                href += "." + suffix;
                String url = getUrl( href, event.getValue(), event.getValueType(), event.getRequestContext().getResponse() );
                ((MapHierarchicalModel<?>)event.getModel() ).addProperty( FIELD_NAME, url );
            }
            return;
        }
    }

    // Spring injection points
    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }
}
