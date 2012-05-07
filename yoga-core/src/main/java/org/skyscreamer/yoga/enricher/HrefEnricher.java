package org.skyscreamer.yoga.enricher;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.ValueReader;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.uri.URICreator;

public class HrefEnricher implements Enricher
{

    private URICreator _uriCreator = new URICreator();

    @Override
    public void enrich(YogaInstanceContext<?> entityContext)
    {
        String urlTemplate = determineTemplate( 
                entityContext.getInstanceType(),
                entityContext.getPopulator() );

        if (urlTemplate != null)
        {
            YogaRequestContext requestContext = entityContext.getRequestContext();
            String urlSuffix = requestContext.getUrlSuffix();
            if (urlSuffix != null)
            {
                urlTemplate += "." + urlSuffix;
            }
            String url = getUrl( urlTemplate, entityContext );
            entityContext.getModel().addSimple( SelectorParser.HREF, url );
        }
    }

    protected String determineTemplate(Class<?> instanceType, FieldPopulator populator)
    {
        if (instanceType.isAnnotationPresent( URITemplate.class ))
        {
            return instanceType.getAnnotation( URITemplate.class ).value();
        }
        else if (populator != null && populator.getUriTemplate() != null)
        {
            return populator.getUriTemplate();
        }
        return null;
    }

    protected String getUrl(String uriTemplate, final YogaInstanceContext<?> entityContext)
    {
        return _uriCreator.getHref( uriTemplate, entityContext.getRequestContext().getResponse(), new ValueReader()
        {
            @Override
            public Object getValue(String property)
            {
                try
                {
                    return PropertyUtils.getNestedProperty( entityContext.getInstance(), property );
                }
                catch (Exception e)
                {
                    throw new YogaRuntimeException( "Could not invoke getter for property " + property
                            + " on class " + entityContext.getInstanceType().getName(), e );
                }
            }
        } );
    }

}
