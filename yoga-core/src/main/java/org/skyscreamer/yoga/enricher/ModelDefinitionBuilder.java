package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.mapper.YogaInstanceContext;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.SelectorParser;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.skyscreamer.yoga.populator.FieldPopulatorUtil.getPopulatorExtraFieldMethods;

public class ModelDefinitionBuilder implements Enricher
{

    @Override
    public void enrich( YogaInstanceContext<?> entityContext )
    {
        if ( !(entityContext.getFieldSelector() instanceof CoreSelector) )
        {
            return;
        }

        List<String> definition = new ArrayList<String>();

        FieldPopulator populator = entityContext.getPopulator();
        if ( populator != null && populator.getSupportedFields() != null )
        {
            definition = populator.getSupportedFields();
        }
        else
        {
            Class<?> instanceType = entityContext.getInstanceType();
            for ( PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ) )
            {
                definition.add( property.getName() );
            }

            for ( Method method : getPopulatorExtraFieldMethods( populator, instanceType ) )
            {
                ExtraField extraField = method.getAnnotation( ExtraField.class );
                definition.add( extraField.value() );
            }
        }
        entityContext.getModel().addSimple( SelectorParser.DEFINITION, definition );
    }

}
