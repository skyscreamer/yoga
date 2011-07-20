package org.skyscreamer.yoga.util;

public class NameUtil
{
   public static String getName(Class<?> type)
   {
       String className = type.getName();
       String split[] = className.split( "[.$]" );
       className = split[split.length - 1];
       return new StringBuilder().append( Character.toLowerCase( className.charAt( 0 ) ) )
               .append( className.substring( 1 ) ).toString();
   }
}
