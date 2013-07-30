package org.skyscreamer.yoga.demo.test.jersey.standalone;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.dto.UserConfiguration;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.Artist;
import org.skyscreamer.yoga.demo.model.Song;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.demo.test.BeanContext;
import org.skyscreamer.yoga.demo.test.jersey.standalone.dao.DemoData;
import org.skyscreamer.yoga.demo.test.jersey.standalone.dao.DemoDataGenericDao;
import org.skyscreamer.yoga.demo.test.jersey.standalone.resources.AlbumResource;
import org.skyscreamer.yoga.demo.test.jersey.standalone.resources.ArtistResource;
import org.skyscreamer.yoga.demo.test.jersey.standalone.resources.SongResource;
import org.skyscreamer.yoga.demo.test.jersey.standalone.resources.UserResource;
import org.skyscreamer.yoga.demo.util.TestUtil;
import org.skyscreamer.yoga.jaxrs.resource.MetaDataController;
import org.skyscreamer.yoga.jaxrs.view.AbstractSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.SelectorBuilderMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.StreamingJsonSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jaxrs.view.XmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jersey.config.YogaMediaTypes;
import org.skyscreamer.yoga.listener.CountLimitRenderingListener;
import org.skyscreamer.yoga.listener.HrefListener;
import org.skyscreamer.yoga.listener.MetadataLinkListener;
import org.skyscreamer.yoga.listener.ModelDefinitionListener;
import org.skyscreamer.yoga.listener.NavigationLinksListener;
import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.listener.SelectorBuilderListener;
import org.skyscreamer.yoga.metadata.DefaultMetaDataRegistry;
import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;

import com.sun.jersey.api.core.DefaultResourceConfig;

public class DemoApplication extends DefaultResourceConfig
{

    public DemoApplication()
    {

        DemoData data = new DemoData();
        try
        {
            data.init();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        final GenericDao dao = new DemoDataGenericDao(  data );
        YogaEntityConfiguration<User> userConfig = new UserConfiguration( dao );
        EntityConfigurationRegistry configurationRegistry = new DefaultEntityConfigurationRegistry( userConfig );
        CoreSelector selector = new CoreSelector( configurationRegistry );
        MetaDataRegistry metaDataRegistry = createMetaDataRegistry( selector );
        HrefListener hrefListener = new HrefListener( configurationRegistry );

        RenderingListenerRegistry renderingListenerRegistry = new RenderingListenerRegistry( 
                new CountLimitRenderingListener( 2000 ),
                hrefListener,
                new SelectorBuilderListener(),
                new NavigationLinksListener( hrefListener ),
                new ModelDefinitionListener(),
                new MetadataLinkListener( metaDataRegistry )
        );

        GDataSelectorParser selectorParser = new GDataSelectorParser();

        getSingletons().add( new AlbumResource( dao ) );
        getSingletons().add( new ArtistResource( dao ) );
        getSingletons().add( new SongResource( dao ) );
        getSingletons().add( new UserResource( dao ) );
        getSingletons().add( new MetaDataController(metaDataRegistry) );

        configureView( new StreamingJsonSelectorMessageBodyWriter(), renderingListenerRegistry, selector, selectorParser );
        configureView( new XmlSelectorMessageBodyWriter(), renderingListenerRegistry, selector, selectorParser );
        configureView( new SelectorBuilderMessageBodyWriter(), renderingListenerRegistry, selector, selectorParser );
        
        TestUtil.setContext( new BeanContext()
        {
            @SuppressWarnings( "unchecked" )
            @Override
            public <T> T getBean( Class<T> type )
            {
                if( GenericDao.class == type )
                {
                    return (T) dao;
                }
                return null;
            }
        } );
    }

    private void configureView(
            AbstractSelectorMessageBodyWriter writer,
            RenderingListenerRegistry renderingListenerRegistry,
            CoreSelector selector, GDataSelectorParser selectorParser )
    {
        writer.setRenderingListenerRegistry( renderingListenerRegistry );
        writer.setSelector( selector );
        writer.setSelectorParser( selectorParser );
        getSingletons().add( writer );
    }

    private DefaultMetaDataRegistry createMetaDataRegistry( CoreSelector selector )
    {
        DefaultMetaDataRegistry metaDataRegistry = new DefaultMetaDataRegistry();
        metaDataRegistry.setCoreSelector( selector );
        metaDataRegistry.setRootMetaDataUrl( MetaDataController.ROOT );
        metaDataRegistry.registerTypeMappings( Album.class, Artist.class, Song.class, User.class );
        return metaDataRegistry;
    }

    @Override
    public Map<String, MediaType> getMediaTypeMappings()
    {
        return YogaMediaTypes.getMediaTypeMappings();
    }

}
