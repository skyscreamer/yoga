package org.skyscreamer.yoga.metadata;

import java.util.Map;

public interface MetaDataService
{
   Map<String, Class<?>> getTypeMappings();
   
   Class<?> getTypeForName(String name);
   
   String getNameForType(Class<?> type);
}
