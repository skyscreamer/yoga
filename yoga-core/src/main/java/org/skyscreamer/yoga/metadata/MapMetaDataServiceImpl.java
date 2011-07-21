package org.skyscreamer.yoga.metadata;

import java.util.Map;
import java.util.Map.Entry;

public class MapMetaDataServiceImpl implements MetaDataService
{

   private Map<String, Class<?>> _map;

   public void setMap(Map<String, Class<?>> map)
   {
      this._map = map;
   }

   @Override
   public Map<String, Class<?>> getTypeMappings()
   {
      return _map;
   }

   @Override
   public Class<?> getTypeForName(String name)
   {
      return _map.get(name);
   }

   /**
    * given a type, get a name. This takes subclassing into consideration. For
    * now, this will return the first subclass match to the type, not the closest
    */
   @Override
   public String getNameForType(Class<?> type)
   {
      String closeMatch = null;
      for (Entry<String, Class<?>> entry : _map.entrySet())
      {
         if(entry.getValue() == type)
         {
            return entry.getKey();
         }
         if (entry.getValue().isAssignableFrom(type))
         {
            closeMatch = entry.getKey();
         }
      }
      return closeMatch;
   }

}
