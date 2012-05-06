package org.skyscreamer.yoga.enricher;

import javax.servlet.http.HttpServletResponse;

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
            String url = getUrl( requestContext.getResponse(), urlTemplate, entityContext.getInstance() );
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

    protected String getUrl(HttpServletResponse response, String uriTemplate, final Object instance)
    {
        return _uriCreator.getHref( uriTemplate, response, new ValueReader()
        {
            @Override
            public Object getValue(String property)
            {
                try
                {
                    return PropertyUtils.getNestedProperty( instance, property );
                }
                catch (Exception e)
                {
                    throw new YogaRuntimeException( "Could not invoke getter for property " + property
                            + " on class " + instance.getClass().getName(), e );
                }
            }
        } );
    }

}
