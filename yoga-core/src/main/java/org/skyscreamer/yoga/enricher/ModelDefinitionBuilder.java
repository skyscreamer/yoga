package org.skyscreamer.yoga.enricher;

import static org.skyscreamer.yoga.populator.FieldPopulatorUtil.getPopulatorExtraFieldMethods;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class ModelDefinitionBuilder implements RenderingListener
{

    private FieldPopulatorRegistry fieldPopulatorRegistry;

    public void setFieldPopulatorRegistry( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        this.fieldPopulatorRegistry = fieldPopulatorRegistry;
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

        if (fieldPopulatorRegistry != null)
        {
            Object populator = fieldPopulatorRegistry.getFieldPopulator( instanceType );
            if (populator != null)
            {
                for (Method method : getPopulatorExtraFieldMethods( populator, instanceType ))
                {
                    ExtraField extraField = method.getAnnotation( ExtraField.class );
                    definition.add( extraField.value() );
                }
            }
        }
        ( ( MapHierarchicalModel<?>) event.getModel()).addProperty( SelectorParser.DEFINITION, definition );
    }

}
