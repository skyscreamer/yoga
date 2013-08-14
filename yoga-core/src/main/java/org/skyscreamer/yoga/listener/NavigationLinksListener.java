package org.skyscreamer.yoga.listener;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Property;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.LinkedInSelectorParser;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class NavigationLinksListener implements RenderingListener
{
    private UriGenerator uriGenerator;

    public NavigationLinksListener()
    {
        this( new UriGenerator() );
    }

    @Deprecated
    /** use the UriGenerator constructor */
    public NavigationLinksListener( HrefListener hrefListener )
    {
        setUriGenerator( hrefListener.getUriGenerator() );
    }

    public NavigationLinksListener( UriGenerator uriGenerator )
    {
        setUriGenerator( uriGenerator );
    }

    @Deprecated
    /** use setUriGenerator instead */
    public void setHrefListener( HrefListener hrefListener )
    {
        setUriGenerator( hrefListener.getUriGenerator() );
    }

    public void setUriGenerator( UriGenerator uriGenerator )
    {
        this.uriGenerator = uriGenerator;
    }

    @Override
    public <T> void eventOccurred( RenderingEvent<T> event ) throws IOException
    {
        Selector selector = event.getSelector();
        if (event.getType() != RenderingEventType.POJO_CHILD || selector.isInfluencedExternally())
        {
            return;
        }

        Class<T> instanceType = event.getValueType();
        T instance = event.getValue();

        String urlSuffix = event.getRequestContext().getUrlSuffix();

        MapHierarchicalModel<?> navigationLinks = ( ( MapHierarchicalModel<?> ) event.getModel() )
                .createChildMap( "navigationLinks" );
        Collection<Property<T>> fieldNames = getNonSelectedFields( selector, instanceType, instance );

        SelectorParser selectorParser = event.getRequestContext().getSelectorParser();
        String format;
        if (selectorParser instanceof LinkedInSelectorParser)
        {
            format = "%s?selector=:(%s)";
        }
        else if (selectorParser instanceof GDataSelectorParser)
        {
            format = "%s?selector=%s";
        }
        else
        {
            throw new IllegalStateException( "Unknown selector type: " + selectorParser.getClass().getName() );
        }
        for ( Property<?> field : fieldNames )
        {
            String fieldName = field.name();
            MapHierarchicalModel<?> navModel = navigationLinks.createChildMap( fieldName );
            navModel.addProperty( SelectorParser.HREF,
                    uriGenerator.getUrl( event, String.format( format, urlSuffix, fieldName ) ) );
            navModel.addProperty( "name", fieldName );
            navModel.finished();
        }
        navigationLinks.finished();
    }

    public <T> Collection<Property<T>> getNonSelectedFields( Selector selector, Class<T> instanceType, Object instance )
    {
        HashMap<String, Property<T>> fields = new HashMap<String, Property<T>>(
                selector.getAllPossibleFieldMap( instanceType ) );
        for ( Property<T> selected : selector.getSelectedFields( instanceType ) )
        {
            fields.remove( selected.name() );
        }
        return fields.values();
    }
}
