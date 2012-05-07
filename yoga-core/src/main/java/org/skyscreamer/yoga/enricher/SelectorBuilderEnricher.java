package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.selector.CoreSelector;

/**
 * Created by IntelliJ IDEA. User: cpage Date: 12/10/11 Time: 3:59 PM
 */
public class SelectorBuilderEnricher extends HrefEnricher implements Enricher
{
    public static final String FIELD_NAME = "selectorBuilder";

    private String suffix = "yoga";

    @Override
    public void enrich(YogaInstanceContext<?> entityContext)
    {
        if (entityContext.getFieldSelector() instanceof CoreSelector)
        {
            String href = determineTemplate( entityContext.getInstanceType(), entityContext.getPopulator() );

            if (href != null)
            {
                href += "." + suffix;
                entityContext.getModel().addSimple( FIELD_NAME, getUrl( href, entityContext) );
            }
            return;
        }
    }

    // Spring injection points
    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }
}
