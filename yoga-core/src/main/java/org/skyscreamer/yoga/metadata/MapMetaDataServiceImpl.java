package org.skyscreamer.yoga.metadata;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.selector.Core;
import org.skyscreamer.yoga.util.NameUtil;

public class MapMetaDataServiceImpl implements MetaDataService
{

   private Map<String, Class<?>> _typeMappings;
   private Map<Class<?>, String> _typeToStringMap = new HashMap<Class<?>, String>();

   private String rootMetaDataUrl;

   public String getRootMetaDataUrl()
   {
      return rootMetaDataUrl;
   }

   public void setRootMetaDataUrl(String rootMetaDataUrl)
   {
      this.rootMetaDataUrl = rootMetaDataUrl;
   }

   public void setTypeMappings(Map<String, Class<?>> map)
   {
      this._typeMappings = map;
      _typeToStringMap.clear();
      for (Entry<String, Class<?>> entry : map.entrySet())
      {
         _typeToStringMap.put( entry.getValue(), entry.getKey() );
      }
   }

   @Override
   public Map<String, Class<?>> getTypeMappings()
   {
      return _typeMappings;
   }

   @Override
   public Class<?> getTypeForName(String name)
   {
      return _typeMappings.get( name );
   }

   /**
    * given a type, get a name. This takes subclassing into consideration. For
    * now, this will return the first subclass match to the type, not the
    * closest
    */
   @Override
   public String getNameForType(Class<?> type)
   {
      if (_typeToStringMap.containsKey( type ))
      {
         return _typeToStringMap.get( type );
      }

      for (Entry<String, Class<?>> entry : _typeMappings.entrySet())
      {
         if (entry.getValue().isAssignableFrom( type ))
         {
            return entry.getKey();
         }
      }
      return null;
   }

   @Override
   public TypeMetaData getMetaData(String name, String suffix)
   {
      return getMetaData( getTypeForName( name ), suffix );
   }

   @Override
   public TypeMetaData getMetaData(Class<?> type, String suffix)
   {
      TypeMetaData result = new TypeMetaData();
      result.setName( NameUtil.getFormalName( type ) );
      for (PropertyDescriptor property : ResultTraverser.getReadableProperties( type ))
      {
         Class<?> propertyType = property.getPropertyType();
         PropertyMetaData propertyMetaData = new PropertyMetaData();
         result.getPropertyMetaData().add( propertyMetaData );
         if (ResultTraverser.isPrimitive( propertyType ))
         {
            setValues( property, propertyType, propertyMetaData );
         }
         else if (Iterable.class.isAssignableFrom( propertyType ))
         {
            Class<?> collectionValueType = getIterableType( property.getReadMethod()
                  .getGenericReturnType() );
            String typeName = NameUtil.getFormalName( collectionValueType ) + "[]";
            setValues( property, typeName, propertyMetaData );
            addHref( propertyMetaData, collectionValueType, suffix );
         }
         else
         {
            setValues( property, NameUtil.getFormalName( propertyType ), propertyMetaData );
            addHref( propertyMetaData, propertyType, suffix );
         }
      }
      return result;
   }

   protected void addHref(PropertyMetaData propertyMetaData, Class<?> propertyType, String suffix)
   {
      String nameForType = getNameForType( propertyType );
      if (nameForType != null)
      {
         propertyMetaData.setHref( getRootMetaDataUrl() + nameForType + "." + suffix );
      }
   }

   protected Class<?> getIterableType(Type type)
   {
      if (type instanceof ParameterizedType)
      {
         ParameterizedType zType = (ParameterizedType) type;
         return (Class<?>) zType.getActualTypeArguments()[0];
      }
      return null;
   }

   protected void setValues(PropertyDescriptor property, Class<?> propertyType,
         PropertyMetaData propertyMetaData)
   {
      String type = propertyType == String.class ? "String" : propertyType.getName();
      setValues( property, type, propertyMetaData );
   }

   protected void setValues(PropertyDescriptor property, String propertyType,
         PropertyMetaData propertyMetaData)
   {
      propertyMetaData.setName( property.getName() );
      propertyMetaData.setType( propertyType );
      propertyMetaData.setIsCore( isCore( property ) );
   }

   protected boolean isCore(PropertyDescriptor property)
   {
      return property.getReadMethod().isAnnotationPresent( Core.class );
   }

}
