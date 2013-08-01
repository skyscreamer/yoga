package org.skyscreamer.yoga.demo.test.jersey.guice;

import java.util.HashMap;
import java.util.Map;

import org.skyscreamer.yoga.builder.YogaBuilder;
import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.jaxrs.resources.AbstractResource;
import org.skyscreamer.yoga.jaxrs.exceptionhandlers.EntityCountExceededExceptionExceptionMapper;
import org.skyscreamer.yoga.jaxrs.resource.MetaDataController;
import org.skyscreamer.yoga.jaxrs.view.builder.SelectorBuilderMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.StreamingJsonSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.XhtmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.XmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jersey.config.URIExtensionsConfig;
import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class WebConfig extends GuiceServletContextListener
{

    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector( new ServletModule()
        {
            @Override
            protected void configureServlets()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "javax.ws.rs.Application", URIExtensionsConfig.class.getName() );
                params.put( "com.sun.jersey.config.property.packages", AbstractResource.class.getPackage().getName() );

                // This part is a bit hack-tastic. We don't have full Guice
                // configuration, so let's let Spring
                // do its thing with the database components and other
                // configuration. This allows us to test
                // the Writer/Resource aspects that are specific to
                // Jersey/Yoga integration. It's probably
                // worth while doing a full guice / hibernate integration at
                // some point.
                ApplicationContext spring = WebApplicationContextUtils
                        .getRequiredWebApplicationContext( getServletContext() );
                Class<?>[] types = { GenericDao.class, MetaDataRegistry.class, YogaBuilder.class, YogaBuilderViewFactory.class };

                for ( Class<?> type : types )
                    bind( spring, type );

                bind( StreamingJsonSelectorMessageBodyWriter.class );
                bind( SelectorBuilderMessageBodyWriter.class );
                bind( XmlSelectorMessageBodyWriter.class );
                bind( XhtmlSelectorMessageBodyWriter.class );
                bind( MetaDataController.class );
                bind( EntityCountExceededExceptionExceptionMapper.class );

                serve( "*.yoga", "*.json", "*.xml", "*.xhtml" ).with( GuiceContainer.class, params );
            }

            private <T> void bind( ApplicationContext spring, Class<T> type )
            {
                T bean = spring.getBean( type );
                bind( type ).toInstance( bean );
            }
        } );
    }

}
