package org.skyscreamer.yoga.builder;

import org.skyscreamer.yoga.view.AbstractYogaView;
import org.skyscreamer.yoga.view.JsonSelectorView;
import org.skyscreamer.yoga.view.SelectorBuilderView;
import org.skyscreamer.yoga.view.StreamingJsonSelectorView;
import org.skyscreamer.yoga.view.XhtmlSelectorView;
import org.skyscreamer.yoga.view.XmlSelectorView;

public class YogaBuilderViewFactory
{
    YogaBuilder builder;

    public YogaBuilderViewFactory()
    {
    }

    public YogaBuilderViewFactory( YogaBuilder builder )
    {
        this.builder = builder;
    }

    public void setBuilder( YogaBuilder builder )
    {
        this.builder = builder;
    }

    public XmlSelectorView createXmlSelectorView()
    {
        return injectViewDependencies( new XmlSelectorView() );
    }

    public JsonSelectorView createJsonSelectorView()
    {
        return injectViewDependencies( new JsonSelectorView() );
    }

    public StreamingJsonSelectorView createStreamingJsonSelectorView()
    {
        return injectViewDependencies( new StreamingJsonSelectorView() );
    }

    public XhtmlSelectorView createXhtmlSelectorView()
    {
        return injectViewDependencies( new XhtmlSelectorView() );
    }

    public SelectorBuilderView createSelectorBuilderView()
    {
        return injectViewDependencies( new SelectorBuilderView() );
    }

    public <T extends AbstractYogaView> T injectViewDependencies( T yogaView )
    {
        builder.finalize();
        yogaView.setRegistry( builder.getRegistry() );
        yogaView.setClassFinderStrategy( builder.getClassFinderStrategy() );
        yogaView.setSelectorResolver( builder.getSelectorResolver() );
        return yogaView;
    }
}
