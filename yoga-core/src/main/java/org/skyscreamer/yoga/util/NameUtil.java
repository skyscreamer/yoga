package org.skyscreamer.yoga.util;


public class NameUtil
{
   public static String getName(Class<?> type)
   {
      String split[] = type.getName().split("[.$]");
      String name = split[split.length - 1];
      return new StringBuilder().append(Character.toLowerCase(name.charAt(0)))
            .append(name.substring(1)).toString();
   }
}
