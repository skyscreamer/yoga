package org.skyscreamer.yoga.mapper;

import static org.skyscreamer.yoga.populator.FieldPopulatorUtil.getPopulatorExtraFieldMethods;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.mapper.enrich.Enricher;
import org.skyscreamer.yoga.mapper.enrich.HrefEnricher;
import org.skyscreamer.yoga.mapper.enrich.ModelDefinitionBuilder;
import org.skyscreamer.yoga.mapper.enrich.NavigationLinksEnricher;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.ExtraField;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public class ResultTraverser
{
   private int _maxEntities = -1;
   private FieldPopulatorRegistry _fieldPopulatorRegistry = new DefaultFieldPopulatorRegistry(
         new ArrayList<FieldPopulator<?>>() );
   private List<Enricher> _enrichers = Arrays.asList(new HrefEnricher(), new ModelDefinitionBuilder(), new NavigationLinksEnricher());
   private ClassFinderStrategy _classFinderStrategy = new ClassFinderStrategy()
   {
      public Class<?> findClass(Object instance)
      {
         return instance.getClass();
      }
   };

   public void traverse(Object instance, Selector fieldSelector, HierarchicalModel model,
         String hrefSuffix, HttpServletResponse response)
   {
      if (_maxEntities > -1) {
          model = new ObservedHierarchicalModel(model, new HierarchicalModelEntityCounter(_maxEntities));
      }
      Class<?> instanceType = findClass( instance );
      addExtraInfo( response, instance, fieldSelector, model, instanceType, hrefSuffix );
      addProperties( response, instance, fieldSelector, model, instanceType, hrefSuffix );
   }

   @SuppressWarnings("unchecked")
   protected <T> void addExtraInfo(HttpServletResponse response, Object instance,
         Selector fieldSelector, HierarchicalModel model, Class<T> instanceType, String hrefSuffix)
   {
	  FieldPopulator<T> populator = (FieldPopulator<T>) _fieldPopulatorRegistry.getFieldPopulator( instanceType );

      for (Enricher enricher : _enrichers)
      {
         enricher.enrich(response, instance, fieldSelector, model, instanceType, hrefSuffix, populator);
      }
      
      addAnnotatedExtraFields( response, fieldSelector, model, hrefSuffix, populator, instance, instanceType );
   }

   private void addAnnotatedExtraFields(HttpServletResponse response, Selector fieldSelector,
         HierarchicalModel model, String hrefSuffix, FieldPopulator<?> populator, Object instance, Class<?> instanceType)
   {
      for (Method method : getPopulatorExtraFieldMethods( populator, instanceType ))
      {
         ExtraField extraField = method.getAnnotation( ExtraField.class );
         if (fieldSelector.containsField( extraField.value() ))
         {
            Object fieldValue;
            try
            {
               if (method.getParameterTypes().length == 0)
               {
                  fieldValue = method.invoke( populator );
               }
               else
               {
                  fieldValue = method.invoke( populator, instance );
               }
            }
            catch (Exception e)
            {
               continue;
            }

            Selector childSelector = fieldSelector.getField( extraField.value() );
            if (isPrimitive( fieldValue.getClass() ))
            {
               if (fieldValue != null)
               {
                  model.addSimple( extraField.value(), fieldValue );
               }
            }
            else if (Iterable.class.isAssignableFrom( fieldValue.getClass() ))
            {
               traverseIterable( childSelector, model, extraField.value(),
                     (Iterable<?>) fieldValue, hrefSuffix, response );
            }
            else
            {
               traverseChild( response, childSelector, model, extraField.value(), fieldValue, hrefSuffix );
            }
         }
      }
   }

   protected void addProperties(HttpServletResponse response, Object instance, Selector fieldSelector,
         HierarchicalModel model, Class<?> instanceType, String hrefSuffix)
   {
      FieldPopulator<?> fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( instanceType );
      for (PropertyDescriptor property : PropertyUtil.getReadableProperties(instanceType))
      {
         try
         {
            boolean containsField = fieldSelector.containsField( property, fieldPopulator );
            if (containsField)
            {
               Object value = PropertyUtils.getNestedProperty( instance, property.getName() );

               Class<?> propertyType = property.getPropertyType();
               if (isPrimitive( propertyType ))
               {
                  if (value != null)
                  {
                     model.addSimple( property, value );
                  }
               }
               else if (Iterable.class.isAssignableFrom( propertyType ))
               {
                  traverseIterable( response, fieldSelector, model, property, (Iterable<?>) value, hrefSuffix );
               }
               else
               {
                  traverseChild( response, fieldSelector, model, property, value, hrefSuffix );
               }
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException( e );
         }
      }
   }

   private void traverseIterable(HttpServletResponse response, Selector fieldSelector,
         HierarchicalModel model, PropertyDescriptor property, Iterable<?> list, String hrefSuffix)
   {
      if (list == null)
      {
         return;
      }
      HierarchicalModel listModel = model.createList( property );
      for (Object o : list)
      {
         Class<?> type = findClass( o );
         if (isPrimitive( type ))
         {
            listModel.addSimple( property, list );
         }
         else
         {
            traverseChild( response, fieldSelector, listModel, property, o, hrefSuffix );
         }
      }
   }

   private void traverseIterable(Selector fieldSelector, HierarchicalModel model, String property,
         Iterable<?> list, String hrefSuffix, HttpServletResponse response)
   {
      if (list == null)
      {
         return;
      }
      HierarchicalModel listModel = model.createList( property );
      for (Object o : list)
      {
         Class<?> type = findClass( o );
         if (isPrimitive( type ))
         {
            listModel.addSimple( property, list );
         }
         else
         {
            traverseChild( response, fieldSelector, listModel, property, o, hrefSuffix );
         }
      }
   }

   private void traverseChild(HttpServletResponse response, Selector parentSelector,
         HierarchicalModel parent, PropertyDescriptor property, Object value, String hrefSuffix)
   {
      traverse( value, parentSelector.getField( property ), parent.createChild( property ),
            hrefSuffix, response );
   }

   private void traverseChild(HttpServletResponse response, Selector parentSelector, HierarchicalModel parent,
         String property, Object value, String hrefSuffix)
   {
      traverse( value, parentSelector.getField( property ), parent.createChild( property ),
            hrefSuffix, response );
   }

   public Class<?> findClass(Object instance)
   {
      return _classFinderStrategy.findClass( instance );
   }

   public static boolean isPrimitive(Class<?> clazz)
   {
      return clazz.isPrimitive() || clazz.isEnum() || Number.class.isAssignableFrom( clazz )
            || String.class.isAssignableFrom( clazz ) || Boolean.class.isAssignableFrom( clazz )
            || Character.class.isAssignableFrom( clazz );
   }

   public void setFieldPopulatorRegistry(FieldPopulatorRegistry fieldPopulatorRegistry)
   {
      _fieldPopulatorRegistry = fieldPopulatorRegistry;
   }

   public void setClassFinderStrategy(ClassFinderStrategy classFinderStrategy)
   {
      _classFinderStrategy = classFinderStrategy;
   }

   public void setEnrichers(List<Enricher> enrichers)
   {
      this._enrichers = enrichers;
   }

    public int getMaxEntities() {
        return _maxEntities;
    }

    public void setMaxEntities(int _maxEntities) {
        this._maxEntities = _maxEntities;
    }
}
