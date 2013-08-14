package org.skyscreamer.yoga.jaxrs.view;

import javax.inject.Inject;

import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.view.AbstractYogaView;

public class AbstractInjectedYogaMessageBodyWriter extends BaseYogaMessageBodyWriter
{

    public AbstractInjectedYogaMessageBodyWriter( AbstractYogaView view )
    {
        super( view );
    }

    @Inject
    public void setSelectorResolver( SelectorResolver selectorResolver )
    {
        _view.setSelectorResolver( selectorResolver );
    }

    @Inject
    public void setRenderingListenerRegistry( RenderingListenerRegistry renderingListenerRegistry ) 
    {
        _view.setRegistry( renderingListenerRegistry );
    }

    @Inject
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        _view.setClassFinderStrategy( classFinderStrategy );
    }

}
