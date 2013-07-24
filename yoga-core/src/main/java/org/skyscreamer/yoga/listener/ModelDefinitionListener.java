package org.skyscreamer.yoga.listener;

import java.io.IOException;

import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Property;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class ModelDefinitionListener implements RenderingListener
{

    @Override
    public <T> void eventOccurred( RenderingEvent<T> event ) throws IOException
    {
        if (event.getType() != RenderingEventType.POJO_CHILD || event.getSelector().isInfluencedExternally())
        {
            return;
        }

        MapHierarchicalModel<?> model = ( MapHierarchicalModel<?>) event.getModel();
        ListHierarchicalModel<?> definitionModel = model.createChildList( SelectorParser.DEFINITION );

        Class<T> instanceType = event.getValueType();
        for (Property<T> property : event.getSelector().getAllPossibleFieldMap( instanceType ).values() )
        {
            definitionModel.addValue( property.name() );
        }
        definitionModel.finished();
        
    }
}
