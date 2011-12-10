package org.skyscreamer.yoga.metadata;

import java.util.Map;

public interface MetaDataService
{
   Map<String, Class<?>> getTypeMappings();

   Class<?> getTypeForName(String name);

   String getNameForType(Class<?> type);

   TypeMetaData getMetaData(String type, String suffix);

   TypeMetaData getMetaData(Class<?> type, String suffix);

   String getHref(Class<?> type, String suffix);

}
