package org.skyscreamer.yoga.listener;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.uri.URICreator;
import org.skyscreamer.yoga.util.ValueReader;

public class HrefListener implements RenderingListener
{

    private URICreator _uriCreator = new URICreator();
    private EntityConfigurationRegistry _entityConfigurationRegistry = new DefaultEntityConfigurationRegistry();

    public HrefListener()
    {
    }

    public HrefListener( EntityConfigurationRegistry _entityConfigurationRegistry)
    {
        this._entityConfigurationRegistry = _entityConfigurationRegistry;
    }

    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry)
    {
        this._entityConfigurationRegistry = entityConfigurationRegistry;
    }

    @Override
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD)
        {
            return;
        }
        YogaRequestContext requestContext = event.getRequestContext();
        Class<?> valueType = event.getValueType();
        MapHierarchicalModel<?> model = (MapHierarchicalModel<?>) event.getModel();

        addUrl( event.getValue(), valueType, requestContext.getUrlSuffix(), model, requestContext );
    }

    public void addUrl( Object value, Class<?> valueType, String urlSuffix,
            MapHierarchicalModel<?> model, YogaRequestContext context )
    {
        String urlTemplate = determineTemplate( valueType );

        if (urlTemplate != null)
        {
            if (urlSuffix != null)
            {
                urlTemplate += "." + urlSuffix;
            }
            String url = getUrl( urlTemplate, value, valueType, context.getResponse() );
            model.addProperty( SelectorParser.HREF, url );
        }
    }

    protected String determineTemplate( Class<?> instanceType )
    {
        String uriTemplate = null;

        YogaEntityConfiguration<?> entityConfiguration = _entityConfigurationRegistry == null ? null
                : _entityConfigurationRegistry.getEntityConfiguration( instanceType );

        // YogaEntityConfiguration trumps annotation
        if (entityConfiguration != null && entityConfiguration.getURITemplate() != null)
        {
            uriTemplate = entityConfiguration.getURITemplate();
        }
        // Annotation based config
        else if (instanceType.isAnnotationPresent( URITemplate.class ))
        {
            uriTemplate = instanceType.getAnnotation( URITemplate.class ).value();
        }

        return uriTemplate;
    }

    public String getUrl( String uriTemplate, final Object value, final Class<?> valueType,
            HttpServletResponse response )
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
                catch (Exception e)
                {
                    throw new YogaRuntimeException( "Could not invoke getter for property "
                            + property + " on class " + valueType.getName(), e );
                }
            }
        } );
    }
}
