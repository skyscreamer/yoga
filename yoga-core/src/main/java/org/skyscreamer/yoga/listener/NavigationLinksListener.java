package org.skyscreamer.yoga.listener;

import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Property;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.LinkedInSelectorParser;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NavigationLinksListener implements RenderingListener
{
    private HrefListener _hrefListener = new HrefListener();

    public void setHrefListener( HrefListener hrefListener )
    {
        this._hrefListener = hrefListener;
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

        MapHierarchicalModel<?> navigationLinks = ((MapHierarchicalModel<?>) event.getModel())
                .createChildMap( "navigationLinks" );
        Collection<Property<T>> fieldNames = getNonSelectedFields( selector, instanceType, instance );

        SelectorParser selectorParser = event.getRequestContext().getSelectorParser();
        String format;
		if (selectorParser instanceof LinkedInSelectorParser) {
            format = "%s?selector=:(%s)";
        }
        else if (selectorParser instanceof GDataSelectorParser) {
            format = "%s?selector=%s";
        }
        else {
            throw new IllegalStateException("Unknown selector type: " + selectorParser.getClass().getName());
        }
        for (Property<?> field : fieldNames)
        {
            String fieldName = field.name();
            MapHierarchicalModel<?> navModel = navigationLinks.createChildMap( fieldName );
            navModel.addProperty( SelectorParser.HREF, _hrefListener.getUrl( event, String.format( format, urlSuffix, fieldName ) ) );
            navModel.addProperty( "name", fieldName );
            navModel.finished();
        }
    }

    public <T> Collection<Property<T>> getNonSelectedFields( Selector selector, Class<T> instanceType,
            Object instance )
    {
        Collection<Property<T>> fieldNames = new ArrayList<Property<T>>(
                selector.getAllPossibleFields( instanceType ) );
        Iterable<Property<T>> selectedFields = selector.getSelectedFields( instanceType );
        for (Iterator<Property<T>> iterator = fieldNames.iterator(); iterator.hasNext();)
        {
            Property<T> property = iterator.next();
            for (Property<T> selected : selectedFields)
            {
                if (selected.name().equals( property.name() ))
                {
                    iterator.remove();
                }
            }
        }
        return fieldNames;
    }
}
