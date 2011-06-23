package org.skyscreamer.yoga.resteasy.util;

import javax.persistence.Entity;

import org.springframework.util.StringUtils;

public class NameUtil
{
   public static String getName(Class<?> type)
   {
      Entity entity = type.getAnnotation(Entity.class);
      if (entity != null && StringUtils.hasText(entity.name()))
      {
         return entity.name();
      }
      else
      {
         String className = type.getName();
         String split[] = className.split("[.$]");
         className = split[split.length - 1];
         return new StringBuilder().append(Character.toLowerCase(className.charAt(0)))
               .append(className.substring(1)).toString();
      }

   }
}
