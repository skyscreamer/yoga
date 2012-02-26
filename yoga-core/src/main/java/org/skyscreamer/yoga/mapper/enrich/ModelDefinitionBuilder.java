package org.skyscreamer.yoga.mapper.enrich;

import static org.skyscreamer.yoga.populator.FieldPopulatorUtil.getPopulatorExtraFieldMethods;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.ExtraField;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;

public class ModelDefinitionBuilder implements Enricher
{

   @Override
   public void enrich(HttpServletResponse response, Object instance, Selector fieldSelector,
         HierarchicalModel model, Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator)
   {
      if (!(fieldSelector instanceof CoreSelector))
      {
         return;
      }
      
      List<String> definition = new ArrayList<String>();

      if (populator != null && populator.getSupportedFields() != null)
      {
         definition = populator.getSupportedFields();
      }
      else
      {
         for (PropertyDescriptor property : PropertyUtil.getReadableProperties( instanceType ))
         {
            definition.add( property.getName() );
         }

         for (Method method : getPopulatorExtraFieldMethods( populator, instanceType ))
         {
            ExtraField extraField = method.getAnnotation( ExtraField.class );
            definition.add( extraField.value() );
         }
      }
      model.addSimple( SelectorParser.DEFINITION, definition );
   }

}
