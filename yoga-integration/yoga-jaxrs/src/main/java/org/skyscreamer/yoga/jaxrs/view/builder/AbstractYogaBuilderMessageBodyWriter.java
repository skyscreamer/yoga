package org.skyscreamer.yoga.jaxrs.view.builder;

import javax.inject.Inject;

import org.skyscreamer.yoga.builder.YogaBuilderViewFactory;
import org.skyscreamer.yoga.jaxrs.view.BaseYogaMessageBodyWriter;
import org.skyscreamer.yoga.view.AbstractYogaView;

public class AbstractYogaBuilderMessageBodyWriter extends BaseYogaMessageBodyWriter
{

    public AbstractYogaBuilderMessageBodyWriter( AbstractYogaView view )
    {
        super( view );
    }

    @Inject
    public void setYogaBuilderUtil( YogaBuilderViewFactory util )
    {
        util.injectViewDependencies( _view );
    }

}
