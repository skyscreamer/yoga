package org.skyscreamer.yoga.demo.test.jersey.guice;

import java.util.HashMap;
import java.util.Map;

import org.skyscreamer.yoga.builder.YogaBuilder;
import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.classfinder.HibernateClassFinderStrategy;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.dao.inmemory.DemoData;
import org.skyscreamer.yoga.demo.dao.inmemory.DemoDataGenericDao;
import org.skyscreamer.yoga.demo.dto.UserConfiguration;
import org.skyscreamer.yoga.demo.jaxrs.resources.AbstractResource;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.demo.test.MapBeanConext;
import org.skyscreamer.yoga.demo.util.TestUtil;
import org.skyscreamer.yoga.jaxrs.exceptionhandlers.EntityCountExceededExceptionExceptionMapper;
import org.skyscreamer.yoga.jaxrs.resource.MetaDataController;
import org.skyscreamer.yoga.jaxrs.view.builder.SelectorBuilderMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.StreamingJsonSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.XhtmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.XmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jersey.config.URIExtensionsConfig;
import org.skyscreamer.yoga.metadata.MetaDataRegistry;

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

                DemoData data = new DemoData();
                data.init();

                GenericDao dao = new DemoDataGenericDao(  data );

                YogaBuilder builder = new YogaBuilder()
                    .withClassFinderStrategy( new HibernateClassFinderStrategy() )
                    .withAliasProperties( this.getClass().getClassLoader().getResourceAsStream( "selectorAlias.properties" ) )
                    .withOutputCountLimit( 2000 )
                    .enableStarAsAllFields()
                    .enableYogaLinks()
                    .registerYogaMetaDataClasses( User.class, Album.class, Artist.class, Song.class )
                    .registerEntityConfigurations( new UserConfiguration( dao ) );

                bind( GenericDao.class ).toInstance( dao );
                bind( YogaBuilderViewFactory.class ).toInstance( new YogaBuilderViewFactory( builder ));
                bind( MetaDataRegistry.class ).toInstance( builder.getMetaDataRegistry() );
                bind( StreamingJsonSelectorMessageBodyWriter.class );
                bind( SelectorBuilderMessageBodyWriter.class );
                bind( XmlSelectorMessageBodyWriter.class );
                bind( XhtmlSelectorMessageBodyWriter.class );
                bind( MetaDataController.class );
                bind( EntityCountExceededExceptionExceptionMapper.class );

                serve( "*.yoga", "*.json", "*.xml", "*.xhtml" ).with( GuiceContainer.class, params );

                MapBeanConext context = new MapBeanConext();
                context.register( GenericDao.class, dao );
                TestUtil.setContext( context );

            }

        } );
    }

}
