package org.skyscreamer.yoga.listener;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.selector.Property;
import org.skyscreamer.yoga.uri.URICreator;
import org.skyscreamer.yoga.uri.URIDecorator;
import org.skyscreamer.yoga.util.ValueReader;

public class UriGenerator
{

    private EntityConfigurationRegistry _entityConfigurationRegistry = new DefaultEntityConfigurationRegistry();

    private ConcurrentHashMap<Class<?>, String> templates = new ConcurrentHashMap<Class<?>, String>();

    public UriGenerator()
    {
    }

    public UriGenerator( EntityConfigurationRegistry _entityConfigurationRegistry)
    {
        this._entityConfigurationRegistry = _entityConfigurationRegistry;
    }

    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry)
    {
        this._entityConfigurationRegistry = entityConfigurationRegistry;
    }

    public <T> String getUrl( RenderingEvent<T> event, String suffix ) throws IOException
    {
        String urlTemplate = determineTemplate( event.getValueType() );
        return urlTemplate != null ? getUrl( urlTemplate, suffix, event ) : null;
    }

    public String determineTemplate( Class<?> instanceType )
    {
        String uriTemplate = templates.get(instanceType);

        if(uriTemplate != null)
        {
            return uriTemplate;
        }

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
        else 
        {
            uriTemplate = determineTemplateForUnknownType( instanceType );
        }

        if(uriTemplate != null)
        {
            String existing = templates.putIfAbsent(instanceType, uriTemplate);
            if(existing != null)
            {
                uriTemplate = existing;
        }
        }
        return uriTemplate;
    }

    /** override this if you want custom behavior */
    protected String determineTemplateForUnknownType( Class<?> instanceType )
    {
        return null;
    }

    public <T> String getUrl( String uriTemplate, final String suffix, final RenderingEvent<T> event )
    {
        String url = getUrlVal(uriTemplate, suffix, event);
        return event.getRequestContext().getResponse().encodeURL(url);
    }

    protected <T> String getUrlVal(String uriTemplate, final String suffix, final RenderingEvent<T> event)
    {
        ValueReader reader = new ValueReader()
        {
            @Override
            public Object getValue( String propertyName )
            {
                Property<T> property = event.getSelector().getProperty( event.getValueType(), propertyName );
                return property == null ? null : property.getValue( event.getValue() );
            }
        };
        if(suffix != null) 
        {
            return URICreator.getHref( uriTemplate, reader, new URIDecorator()
            {
                
                @Override
                public StringBuilder decorate(StringBuilder uri)
                {
                    return uri.append(".").append(suffix);
                }
            } );
        }
        else 
        {
            return URICreator.getHref( uriTemplate, reader );
        }
    }
}
