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
import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.selector.parser.DynamicPropertyResolver;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;

public class YogaBuilder
{

    protected boolean _finalized = false;

    protected ClassFinderStrategy _classFinderStrategy = new DefaultClassFinderStrategy();

    protected RenderingListenerRegistry _registry = new RenderingListenerRegistry();

    protected  SelectorResolver _selectorResolver;

    protected InputStream _aliasProperties = null;

    protected boolean _createAllLinks = false;

    private MetaDataRegistry _metaDataRegistry;

    public YogaBuilder()
    {
    	init();
    }

	/**
	 * there are cases where a user might want to override meta data registry,
	 * selector resolver and/or CoreSelector implementations. This would be the
	 * place to do it, since we have complex dependencies for those objects, and
	 * we need those objects set up before other setters are called.
	 */
	protected void init() {
		_metaDataRegistry = new DefaultMetaDataRegistry();
    	_selectorResolver = new SelectorResolver();
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

    public MetaDataRegistry getMetaDataRegistry()
    {
        return _metaDataRegistry;
    }

    // -------------- setters ---------

    /**
     * Hibernate and other ORMs do some funky things to your pojo classes.  A ClassFinderStrategy
     * will convert the funky class object into the original version of the pojo's class. 
     * 
     * @param classFinderStrategy - a ClassFinderStrategy that knows how to introspect pojos. 
     * 
     * @see ClassFinderStrategy
     */
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        checkFinalized();
        this._classFinderStrategy = classFinderStrategy;
    }

    /**
     * Set hand coded yoga configuration objects.  This allows for either a non-annotated pojos and/or some interesting
     * customization of outputs.
     * 
     * @param entityConfigurations
     */
    public void setEntityConfigurations( YogaEntityConfiguration<?>... entityConfigurations )
    {
        checkFinalized();
        this._selectorResolver.getBaseSelector().getEntityConfigurationRegistry().register( entityConfigurations );
    }
    
    /**
     * Allows you to create a RenderingListenerRegistry that contains custom event handlers
     * for yoga events that relate to different sets of data being added to JSon/XML outputs.
     * 
     * @param registry a fully formed RenderingListenerRegistry object with custom listeners
     * 
     * @see RenderingListenerRegistry
     */

    public void setRegistry( RenderingListenerRegistry registry )
    {
        checkFinalized();
        this._registry = registry;
    }

    /**
     * This is a mechanism of allowing small aliases to be converted into pre-configured set of aliases
     * for complicated or common selectors.
     * 
     * @param aliasProperties an Inputstream that contains key/value pairs to be injected into the Propterties 
     */
    public void setAliasProperties( InputStream aliasProperties )
    {
        checkFinalized();
        this._aliasProperties = aliasProperties;
    }

    /**
     * Set the maximum number of values that yoga should emit.  This is a sanity check to make sure that 
     * a user can't take down your server by reading all of your data through recursive calls.  A limit
     * of 10,000 is reasonable.
     */
    public void setOutputCountLimit( int countLimit )
    {
        checkFinalized();
        this._registry.addListener( new CountLimitRenderingListener( countLimit ) );
    }

    /** 
     * Should yoga create metadata links as part of client requests?
     */
    public void setCreateYogaLinks( boolean createAllLinks )
    {
        checkFinalized();
        this._createAllLinks = createAllLinks;
    }

    /**
     * Register the classes that Yoga should know about for the purposes of metadata creation.
     * Yoga can do some interesting things if you register the list of pojos that are the
     * return values of your REST endpoints.
     */
    public void setYogaMetaDataRegisteredClasses( Class<?> ... classes )
    {
        checkFinalized();
        this._metaDataRegistry.registerClasses( classes );
    }

    /**
     * Set the root url to use for metadata requests.
     */
    public void setRootMetaDataUrl( String rootMetaDataUrl )
    {
        checkFinalized();
        this._metaDataRegistry.setRootMetaDataUrl( rootMetaDataUrl );
    }
    
    /**
     * Should field selectors with * be considered as a shortcut for all child fields?
     */
    public void setEnableStarAsAllFields( boolean starAsAll )
    {
        checkFinalized();
        this._selectorResolver.setStarResolvesToAll( starAsAll );
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
    
    public YogaBuilder enableStarAsAllFields()
    {
        setEnableStarAsAllFields( true );
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
