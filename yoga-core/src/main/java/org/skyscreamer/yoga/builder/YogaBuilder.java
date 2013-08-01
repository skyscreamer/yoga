package org.skyscreamer.yoga.builder;

import java.io.InputStream;

import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.listener.CountLimitRenderingListener;
import org.skyscreamer.yoga.listener.HrefListener;
import org.skyscreamer.yoga.listener.MetadataLinkListener;
import org.skyscreamer.yoga.listener.ModelDefinitionListener;
import org.skyscreamer.yoga.listener.NavigationLinksListener;
import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.listener.SelectorBuilderListener;
import org.skyscreamer.yoga.listener.UriGenerator;
import org.skyscreamer.yoga.metadata.DefaultMetaDataRegistry;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.selector.parser.DynamicPropertyResolver;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;

public class YogaBuilder
{

    protected boolean _finalized = false;

    protected ClassFinderStrategy _classFinderStrategy = new DefaultClassFinderStrategy();

    protected RenderingListenerRegistry _registry = new RenderingListenerRegistry();

    protected SelectorResolver _selectorResolver = new SelectorResolver();

    protected InputStream _aliasProperties = null;

    protected boolean _createAllLinks = false;

    private DefaultMetaDataRegistry _metaDataRegistry = new DefaultMetaDataRegistry();

    {
        _selectorResolver.getBaseSelector().setEntityConfigurationRegistry( new DefaultEntityConfigurationRegistry() );
        _metaDataRegistry.setCoreSelector( this._selectorResolver.getBaseSelector() );
        _metaDataRegistry.setRootMetaDataUrl( "/metadata/" );
    }

    // -------------- helpers ---------
    
    protected void checkFinalized()
    {
        if(_finalized)
            throw new IllegalStateException( "You cannot update builder information after it's been used to generate artifacts" );
    }
    
    // -------------- getters ---------

    public ClassFinderStrategy getClassFinderStrategy()
    {
        return _classFinderStrategy;
    }

    public RenderingListenerRegistry getRegistry()
    {
        return _registry;
    }

    public SelectorResolver getSelectorResolver()
    {
        return _selectorResolver;
    }

    public InputStream getAliasProperties()
    {
        return _aliasProperties;
    }

    public boolean isCreateAllLinks()
    {
        return _createAllLinks;
    }

    public boolean isEnableYogaMetadata()
    {
        return !_metaDataRegistry.getTypes().isEmpty();
    }
    
    public DefaultMetaDataRegistry getMetaDataRegistry()
    {
        return _metaDataRegistry;
    }

    // -------------- setters ---------

    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        checkFinalized();
        this._classFinderStrategy = classFinderStrategy;
    }

    public void setEntityConfigurations( YogaEntityConfiguration<?>... entityConfigurations )
    {
        checkFinalized();
        this._selectorResolver.getBaseSelector().getEntityConfigurationRegistry().register( entityConfigurations );
    }

    public void setRegistry( RenderingListenerRegistry registry )
    {
        checkFinalized();
        this._registry = registry;
    }

    public void setAliasProperties( InputStream aliasProperties )
    {
        checkFinalized();
        this._aliasProperties = aliasProperties;
    }

    public void setOutputCountLimit( int countLimit )
    {
        checkFinalized();
        this._registry.addListener( new CountLimitRenderingListener( countLimit ) );
    }

    public void setCreateYogaLinks( boolean createAllLinks )
    {
        checkFinalized();
        this._createAllLinks = createAllLinks;
    }

    public void setYogaMetaDataRegisteredClasses( Class<?> ... classes )
    {
        checkFinalized();
        this._metaDataRegistry.registerClasses( classes );
    }

    public void setRootMetaDataUrl( String rootMetaDataUrl )
    {
        checkFinalized();
        this._metaDataRegistry.setRootMetaDataUrl( rootMetaDataUrl );
    }

    // fluent interface

    public YogaBuilder withClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        setClassFinderStrategy( classFinderStrategy );
        return this;
    }

    public YogaBuilder withRegistryTraverser( RenderingListenerRegistry registry )
    {
        setRegistry( registry );
        return this;
    }

    public YogaBuilder withAliasProperties( InputStream propertyFile )
    {
        setAliasProperties( propertyFile );
        return this;
    }
    
    public YogaBuilder withOutputCountLimit( int countLimit )
    {
        setOutputCountLimit( countLimit );
        return this;
    }

    public YogaBuilder enableYogaLinks()
    {
        setCreateYogaLinks( true );
        return this;
    }

    public YogaBuilder registerEntityConfigurations( YogaEntityConfiguration<?>... entityConfigurations )
    {
        setEntityConfigurations( entityConfigurations );
        return this;
    }

    public YogaBuilder registerYogaMetaDataClasses( Class<?> ... classes )
    {
        setYogaMetaDataRegisteredClasses( classes );
        return this;
    }
    
    // finalize!
    
    public void finalize()
    {
        if( _finalized )
        {
            return;
        }

        addAliases();

        if( _createAllLinks )
        {
            registerRenderingListeners();
        }

        if( _metaDataRegistry != null )
        {
            registerMetadataLinkListener();
        }
        _finalized = true;
    }

    protected void registerMetadataLinkListener()
    {
        this._registry.addListener( new MetadataLinkListener( getMetaDataRegistry() ) );
    }

    protected void registerRenderingListeners()
    {
        UriGenerator uriGenerator = new UriGenerator(
                this._selectorResolver.getBaseSelector().getEntityConfigurationRegistry() );

        this._registry.addListener( new HrefListener(uriGenerator) );
        this._registry.addListener( new SelectorBuilderListener(uriGenerator) );
        this._registry.addListener( new NavigationLinksListener(uriGenerator) );
        this._registry.addListener( new ModelDefinitionListener() );
    }

    protected void addAliases()
    {
        if( this._aliasProperties != null )
        {
            this._selectorResolver.getSelectorParser().setAliasSelectorResolver( new DynamicPropertyResolver( _aliasProperties ) );
        }
    }

}
