package org.skyscreamer.yoga.demo.test.jersey.standalone;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.skyscreamer.yoga.builder.YogaBuilder;
import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.classfinder.HibernateClassFinderStrategy;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.dao.inmemory.DemoData;
import org.skyscreamer.yoga.demo.dao.inmemory.DemoDataGenericDao;
import org.skyscreamer.yoga.demo.dto.UserConfiguration;
import org.skyscreamer.yoga.demo.jaxrs.resources.AlbumResource;
import org.skyscreamer.yoga.demo.jaxrs.resources.ArtistResource;
import org.skyscreamer.yoga.demo.jaxrs.resources.SongResource;
import org.skyscreamer.yoga.demo.jaxrs.resources.UserResource;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.demo.test.MapBeanConext;
import org.skyscreamer.yoga.demo.util.TestUtil;
import org.skyscreamer.yoga.jaxrs.resource.MetaDataController;
import org.skyscreamer.yoga.jaxrs.view.builder.SelectorBuilderMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.StreamingJsonSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.builder.XmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jersey.config.YogaMediaTypes;

import com.sun.jersey.api.core.DefaultResourceConfig;

public class DemoApplication extends DefaultResourceConfig
{

    public DemoApplication()
    {

        DemoData data = new DemoData();
        data.init();

        GenericDao dao = new DemoDataGenericDao(  data );
        
        YogaBuilder builder = new YogaBuilder()
            .withClassFinderStrategy( new HibernateClassFinderStrategy() )
            .withAliasProperties( this.getClass().getClassLoader().getResourceAsStream( "selectorAlias.properties" ) )
            .withOutputCountLimit( 2000 )
            .enableYogaLinks()
            .registerYogaMetaDataClasses( User.class, Album.class, Artist.class, Song.class )
            .registerEntityConfigurations( new UserConfiguration( dao ) );

        getSingletons().add( new AlbumResource( dao ) );
        getSingletons().add( new ArtistResource( dao ) );
        getSingletons().add( new SongResource( dao ) );
        getSingletons().add( new UserResource( dao ) );
        getSingletons().add( new MetaDataController( builder.getMetaDataRegistry() ) );

        YogaBuilderViewFactory util = new YogaBuilderViewFactory( builder );
        getSingletons().add( new StreamingJsonSelectorMessageBodyWriter( util ) );
        getSingletons().add( new XmlSelectorMessageBodyWriter( util ) );
        getSingletons().add( new SelectorBuilderMessageBodyWriter( util ) );

        MapBeanConext context = new MapBeanConext();
        context.register( GenericDao.class, dao );
        TestUtil.setContext( context );
    }

    @Override
    public Map<String, MediaType> getMediaTypeMappings()
    {
        return YogaMediaTypes.getMediaTypeMappings();
    }

}
