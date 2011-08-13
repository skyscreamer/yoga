package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.ValueReader;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.uri.AnnotationURITemplateGenerator;
import org.skyscreamer.yoga.uri.URICreator;
import org.skyscreamer.yoga.uri.URITemplateGenerator;
import org.skyscreamer.yoga.util.NameUtil;

/**
 * Created by IntelliJ IDEA. User: corby Date: 4/21/11 Time: 3:07 PM
 */
public class ResultTraverser
{
   private Map<Class<?>, FieldPopulator<?>> fieldPopulators = new HashMap<Class<?>, FieldPopulator<?>>();
   private URICreator uriCreator = new URICreator();
   private URITemplateGenerator uriTemplateGenerator = new AnnotationURITemplateGenerator();

   public <T> void registerPopulator(Class<T> type, FieldPopulator<T> populator)
   {
      fieldPopulators.put( type, populator );
   }

   public void traverse(Object instance, Selector fieldSelector, HierarchicalModel model, String hrefSuffix)
   {
      Class<?> instanceType = getClass( instance );
      addExtraInfo( instance, fieldSelector, model, instanceType, hrefSuffix );
      addProperties( instance, fieldSelector, model, instanceType, hrefSuffix );
   }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void addExtraInfo(Object instance, Selector fieldSelector,
            HierarchicalModel model, Class<?> instanceType, String hrefSuffix)
    {
        if (instanceType.isAnnotationPresent(URITemplate.class)) {
            String href = instanceType.getAnnotation(URITemplate.class).value();
            if (hrefSuffix != null)
                href += "." + hrefSuffix;
            model.addSimple(SelectorParser.HREF, getHref(href, instance, model));
        }

        FieldPopulator populator = fieldPopulators.get(instanceType);
        if (populator != null) {
            populator.addExtraFields(fieldSelector, instance, this, model);
        }
    }

   protected void addProperties(Object instance, Selector fieldSelector, HierarchicalModel model,
         Class<?> instanceType, String hrefSuffix)
   {
      for (PropertyDescriptor property : PropertyUtils.getPropertyDescriptors( instanceType ))
      {
         String field = property.getName();
         try
         {
            if (fieldSelector.containsField( property ))
            {
               Object value = PropertyUtils.getNestedProperty( instance, property.getName() );

               Class<?> propertyType = property.getPropertyType();
               if (isNotBean( propertyType ))
               {
                  model.addSimple( property, value );
               }
               else if (Iterable.class.isAssignableFrom( propertyType ))
               {
                  traverseIterable( fieldSelector, model, property, (Iterable<?>) value, hrefSuffix );
               }
               else
               {
                  traverseChild( fieldSelector, model, property, field, value, hrefSuffix );
               }
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException( e );
         }
      }
   }

   protected Object getHref(String uriTemplate, final Object instance, HierarchicalModel model)
   {
      return uriCreator.getHref( uriTemplate, new ValueReader()
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

   public void traverseIterable(Selector fieldSelector, HierarchicalModel model,
         PropertyDescriptor property, Iterable<?> list, String hrefSuffix)
   {
      HierarchicalModel listModel = model.createList( property, list );
      for (Object o : list)
      {
         Class<? extends Object> type = getClass( o );
         if (isNotBean( type ))
         {
            listModel.addSimple( property, list );
         }
         else
         {
            traverseChild( fieldSelector, listModel, property, NameUtil.getName( type ), o, hrefSuffix );
         }
      }
   }

   public void traverseIterable(Selector fieldSelector, HierarchicalModel model, String property,
         Iterable<?> list, String hrefSuffix)
   {
      HierarchicalModel listModel = model.createList( property, list );
      for (Object o : list)
      {
         Class<? extends Object> type = getClass( o );
         if (isNotBean( type ))
         {
            listModel.addSimple( property, list );
         }
         else
         {
            traverseChild( fieldSelector, listModel, property, NameUtil.getName( type ), o, hrefSuffix );
         }
      }
   }

   // allow this to be overridden.
   public void traverseChild(Selector parentSelector, HierarchicalModel parent,
         PropertyDescriptor property, String name, Object value, String hrefSuffix)
   {
      traverse( value, parentSelector.getField( property ), parent.createChild( property, value ), hrefSuffix );
   }

   // allow this to be overridden.
   public void traverseChild(Selector parentSelector, HierarchicalModel parent, String property,
         String name, Object value, String hrefSuffix)
   {
      traverse( value, parentSelector.getField( property ), parent.createChild( property, value ), hrefSuffix );
   }

   // TODO: make this into an interface. This should be a strategy
   public Class<? extends Object> getClass(Object instance)
   {
      return instance.getClass();
   }

   protected boolean isNotBean(Class<?> clazz)
   {
      return clazz.isPrimitive() || clazz.isEnum() || Number.class.isAssignableFrom( clazz )
            || String.class.isAssignableFrom( clazz ) || Boolean.class.isAssignableFrom( clazz )
            || Character.class.isAssignableFrom( clazz );
   }

   // setters
   public Map<Class<?>, FieldPopulator<?>> getFieldPopulators()
   {
      return fieldPopulators;
   }

   public void setFieldPopulators(Map<Class<?>, FieldPopulator<?>> fieldPopulators)
   {
      this.fieldPopulators = fieldPopulators;
   }

   public URICreator getUriCreator()
   {
      return uriCreator;
   }

   public void setUriCreator(URICreator uriCreator)
   {
      this.uriCreator = uriCreator;
   }

   public URITemplateGenerator getUriTemplateGenerator()
   {
      return uriTemplateGenerator;
   }

   public void setUriTemplateGenerator(URITemplateGenerator uriTemplateGenerator)
   {
      this.uriTemplateGenerator = uriTemplateGenerator;
   }
}
