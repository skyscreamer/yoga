package org.skyscreamer.yoga.mapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.populator.*;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.uri.AnnotationURITemplateGenerator;
import org.skyscreamer.yoga.uri.URICreator;
import org.skyscreamer.yoga.uri.URITemplateGenerator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public class ResultTraverser
{
   public static final boolean SHOW_NAVIGATION_LINKS = true;
   private FieldPopulatorRegistry _fieldPopulatorRegistry = new DefaultFieldPopulatorRegistry(
         new ArrayList<FieldPopulator<?>>() );
   private URICreator _uriCreator = new URICreator();
   private URITemplateGenerator _uriTemplateGenerator = new AnnotationURITemplateGenerator();
   private ClassFinderStrategy _classFinderStrategy = new DefaultClassFinderStrategy();

   private boolean _showDefinition = false;
   private boolean _showHref = true;

   public void traverse(Object instance, Selector fieldSelector, HierarchicalModel model,
         String hrefSuffix)
   {
      Class<?> instanceType = findClass( instance );
      addExtraInfo( instance, fieldSelector, model, instanceType, hrefSuffix );
      addProperties( instance, fieldSelector, model, instanceType, hrefSuffix );
   }

   protected <T> void addExtraInfo(Object instance, Selector fieldSelector,
         HierarchicalModel model, Class<T> instanceType, String hrefSuffix)
   {
      @SuppressWarnings("unchecked")
	  FieldPopulator<T> populator = (FieldPopulator<T>) _fieldPopulatorRegistry.getFieldPopulator( instanceType );

      if (_showHref)
      {
         addHref( instance, model, instanceType, hrefSuffix, populator );
      }

      if (_showDefinition)
      {
         addDefinition( model, instanceType, populator );
      }

      addAnnotatedExtraFields( fieldSelector, model, hrefSuffix, populator, instance, instanceType );
   }

   private void addAnnotatedExtraFields(Selector fieldSelector, HierarchicalModel model,
         String hrefSuffix, FieldPopulator<?> populator, Object instance, Class<?> instanceType)
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
                     (Iterable<?>) fieldValue, hrefSuffix );
            }
            else
            {
               traverseChild( childSelector, model, extraField.value(), fieldValue, hrefSuffix );
            }
         }
      }
   }

   private void addDefinition(HierarchicalModel model, Class<?> instanceType,
         FieldPopulator<?> populator)
   {
      List<String> definition = new ArrayList<String>();

      if (populator != null && populator.getSupportedFields() != null)
      {
         definition = populator.getSupportedFields();
      }
      else
      {
         for (PropertyDescriptor property : getReadableProperties( instanceType ))
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

   private List<Method> getPopulatorExtraFieldMethods(FieldPopulator<?> populator,
         Class<?> instanceType)
   {
      List<Method> result = new ArrayList<Method>();
      if (populator != null)
      {
         for (Method method : populator.getClass().getDeclaredMethods())
         {
            if (method.isAnnotationPresent( ExtraField.class ))
            {
               Class<?>[] parameterTypes = method.getParameterTypes();
               if (parameterTypes.length == 0
                     || (parameterTypes.length == 1 && parameterTypes[0].equals( instanceType )))
               {
                  result.add( method );
               }
            }
         }
      }
      return result;
   }

   private void addHref(Object instance, HierarchicalModel model, Class<?> instanceType,
         String hrefSuffix, FieldPopulator<?> populator)
   {
      String href = null;
      if (instanceType.isAnnotationPresent( URITemplate.class ))
      {
         href = instanceType.getAnnotation( URITemplate.class ).value();
      }
      else if (populator != null && populator.getUriTemplate() != null)
      {
         href = populator.getUriTemplate();
      }

      if (href != null)
      {
         if (hrefSuffix != null)
            href += "." + hrefSuffix;
         model.addSimple( SelectorParser.HREF, getHref( href, instance ) );
      }
   }

   protected void addProperties(Object instance, Selector fieldSelector, HierarchicalModel model,
         Class<?> instanceType, String hrefSuffix)
   {
      FieldPopulator<?> fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( instanceType );
      List<PropertyDescriptor> unshownProperties = new ArrayList<PropertyDescriptor>();
      for (PropertyDescriptor property : getReadableProperties( instanceType ))
      {
         try
         {
            if (fieldSelector.containsField( property, fieldPopulator ))
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
                  traverseIterable( fieldSelector, model, property, (Iterable<?>) value, hrefSuffix );
               }
               else
               {
                  traverseChild( fieldSelector, model, property, value, hrefSuffix );
               }
            }
            else
            {
               unshownProperties.add( property );
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException( e );
         }
      }

      // Extract this into a new method
      if (SHOW_NAVIGATION_LINKS && unshownProperties.size() > 0
            && fieldSelector instanceof CoreSelector)
      {
         HierarchicalModel navigationLinks = model.createChild( "navigationLinks",
               "useless parameter" );
         for (PropertyDescriptor property : unshownProperties)
         {
            HierarchicalModel propertyLink = navigationLinks.createChild( property,
                  "another useless parameter" );
            propertyLink.addSimple( "name", property.getName() );
            String hrefSuffixAndSelector = hrefSuffix + "?selector=:(" + property.getName() + ")";
            addHref( instance, propertyLink, instanceType, hrefSuffixAndSelector, fieldPopulator );
         }
      }
   }

   public static List<PropertyDescriptor> getReadableProperties(Class<?> instanceType)
   {
      List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();
      for (PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors( instanceType ))
      {
         if (descriptor.getReadMethod() != null && !descriptor.getName().equals( "class" ))
         {
            result.add( descriptor );
         }
      }
      return result;
   }

   protected Object getHref(String uriTemplate, final Object instance)
   {
      return _uriCreator.getHref( uriTemplate, new ValueReader()
      {
         @Override
         public Object getValue(String property)
         {
            try
            {
               return PropertyUtils.getNestedProperty( instance, property );
            }
            catch (Exception e)
            {
               throw new RuntimeException( "Could not invoke getter for property " + property
                     + " on class " + instance.getClass().getName(), e );
            }
         }
      } );
   }

   private void traverseIterable(Selector fieldSelector, HierarchicalModel model,
         PropertyDescriptor property, Iterable<?> list, String hrefSuffix)
   {
      if (list == null)
      {
         return;
      }
      HierarchicalModel listModel = model.createList( property, list );
      for (Object o : list)
      {
         Class<?> type = findClass( o );
         if (isPrimitive( type ))
         {
            listModel.addSimple( property, list );
         }
         else
         {
            traverseChild( fieldSelector, listModel, property, o, hrefSuffix );
         }
      }
   }

   private void traverseIterable(Selector fieldSelector, HierarchicalModel model, String property,
         Iterable<?> list, String hrefSuffix)
   {
      if (list == null)
      {
         return;
      }
      HierarchicalModel listModel = model.createList( property, list );
      for (Object o : list)
      {
         Class<?> type = findClass( o );
         if (isPrimitive( type ))
         {
            listModel.addSimple( property, list );
         }
         else
         {
            traverseChild( fieldSelector, listModel, property, o, hrefSuffix );
         }
      }
   }

   private void traverseChild(Selector parentSelector, HierarchicalModel parent,
         PropertyDescriptor property, Object value, String hrefSuffix)
   {
      traverse( value, parentSelector.getField( property ), parent.createChild( property, value ),
            hrefSuffix );
   }

   private void traverseChild(Selector parentSelector, HierarchicalModel parent, String property,
         Object value, String hrefSuffix)
   {
      traverse( value, parentSelector.getField( property ), parent.createChild( property, value ),
            hrefSuffix );
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

   public URICreator getUriCreator()
   {
      return _uriCreator;
   }

   public void setUriCreator(URICreator uriCreator)
   {
      this._uriCreator = uriCreator;
   }

   public URITemplateGenerator getUriTemplateGenerator()
   {
      return _uriTemplateGenerator;
   }

   public void setUriTemplateGenerator(URITemplateGenerator uriTemplateGenerator)
   {
      this._uriTemplateGenerator = uriTemplateGenerator;
   }

   public void setFieldPopulatorRegistry(FieldPopulatorRegistry fieldPopulatorRegistry)
   {
      _fieldPopulatorRegistry = fieldPopulatorRegistry;
   }

   public void setClassFinderStrategy(ClassFinderStrategy classFinderStrategy)
   {
      _classFinderStrategy = classFinderStrategy;
   }

   private class DefaultClassFinderStrategy implements ClassFinderStrategy
   {
      public Class<?> findClass(Object instance)
      {
         return instance.getClass();
      }
   }

   public void setShowDefinition(boolean showDefinition)
   {
      _showDefinition = showDefinition;
   }

   public void setShowHref(boolean showHref)
   {
      _showHref = showHref;
   }
}
