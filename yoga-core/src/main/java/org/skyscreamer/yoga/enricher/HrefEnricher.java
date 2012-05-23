package org.skyscreamer.yoga.enricher;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.uri.URICreator;
import org.skyscreamer.yoga.util.ValueReader;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HrefEnricher implements Enricher
{

    private URICreator _uriCreator = new URICreator();

    @Override
    public void enrich( RenderingEvent event )
    {
        YogaRequestContext requestContext = event.getRequestContext();
        Class<?> valueType = event.getValueType();
        MapHierarchicalModel<?> model = (MapHierarchicalModel<?>) event.getModel();

        addUrl( event.getValue(), valueType, requestContext.getUrlSuffix(), model, requestContext );
    }

    public void addUrl( Object value, Class<?> valueType, String urlSuffix, MapHierarchicalModel<?> model,
            YogaRequestContext context )
    {
        String urlTemplate = determineTemplate( valueType, context );

        if ( urlTemplate != null )
        {
            if ( urlSuffix != null )
            {
                urlTemplate += "." + urlSuffix;
            }
            String url = getUrl( urlTemplate, value, valueType, context.getResponse() );
            model.addProperty( SelectorParser.HREF, url );
        }
    }

    protected String determineTemplate( Class<?> instanceType, YogaRequestContext context )
    {
        String result = null;
        Object fieldPopulator = context.getFieldPopulatorRegistry().getFieldPopulator( instanceType );
        if ( fieldPopulator != null )
        {
            try
            {
                Method method = fieldPopulator.getClass().getMethod( "getURITemplate" );
                result = (String) method.invoke( fieldPopulator );
            }
            catch ( Exception e )
            {
                // getCoreFields not implemented on FieldPopulator
            }
        }

        if ( result == null && instanceType.isAnnotationPresent( URITemplate.class ) )
        {
            result = instanceType.getAnnotation( URITemplate.class ).value();
        }
        return result;
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
