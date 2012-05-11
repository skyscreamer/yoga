package org.skyscreamer.yoga.enricher;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.populator.ValueReader;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.uri.URICreator;

public class HrefEnricher implements Enricher
{

    private URICreator _uriCreator = new URICreator();

    @Override
    public void enrich( RenderingEvent event )
    {
        YogaRequestContext requestContext = event.getRequestContext();
        Class<?> valueType = event.getValueType();
        String urlSuffix = requestContext.getUrlSuffix();
        HierarchicalModel<?> model = event.getModel();
        HttpServletResponse response = requestContext.getResponse();

        addUrl( event.getValue(), valueType, urlSuffix, model, response );
    }

    public void addUrl( Object value, Class<?> valueType, String urlSuffix,
            HierarchicalModel<?> model, HttpServletResponse response )
    {
        String urlTemplate = determineTemplate( valueType );

        if ( urlTemplate != null )
        {
            if ( urlSuffix != null )
            {
                urlTemplate += "." + urlSuffix;
            }
            String url = getUrl( urlTemplate, value, valueType, response );
            model.addProperty( SelectorParser.HREF, url );
        }
    }

    protected String determineTemplate( Class<?> instanceType )
    {
        if ( instanceType.isAnnotationPresent( URITemplate.class ) )
        {
            return instanceType.getAnnotation( URITemplate.class ).value();
        }
        return null;
    }

    public String getUrl( String uriTemplate, final Object value, final Class<?> valueType, HttpServletResponse response )
    {
        return _uriCreator.getHref( uriTemplate, response, new ValueReader()
        {
            @Override
            public Object getValue( String property )
            {
                try
                {
                    return PropertyUtils.getNestedProperty( value, property );
                }
                catch ( Exception e )
                {
                    throw new YogaRuntimeException( "Could not invoke getter for property " + property
                            + " on class " + valueType.getName(), e );
                }
            }
        } );
    }

}
