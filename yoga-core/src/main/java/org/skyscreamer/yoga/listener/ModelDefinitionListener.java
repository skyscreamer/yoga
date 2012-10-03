package org.skyscreamer.yoga.listener;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class ModelDefinitionListener implements RenderingListener
{
    private EntityConfigurationRegistry _entityConfigurationRegistry;

    public void setEntityConfigurationRegistry(EntityConfigurationRegistry entityConfigurationRegistry)
    {
        _entityConfigurationRegistry = entityConfigurationRegistry;
    }

    @Override
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD || event.getSelector().isInfluencedExternally())
        {
            return;
        }

        List<String> definition = new ArrayList<String>();

        Class<?> instanceType = event.getValueType();
        for (PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ))
        {
            definition.add( property.getName() );
        }

        if (_entityConfigurationRegistry != null)
        {
            YogaEntityConfiguration entityConfiguration =
                    _entityConfigurationRegistry.getEntityConfiguration(instanceType);
            if (entityConfiguration != null)
            {
                definition.addAll(entityConfiguration.getExtraFieldNames());
            }
        }
        ( ( MapHierarchicalModel<?>) event.getModel()).addProperty( SelectorParser.DEFINITION, definition );
    }
}
