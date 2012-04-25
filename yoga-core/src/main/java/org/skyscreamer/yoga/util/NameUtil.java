package org.skyscreamer.yoga.util;

public class NameUtil
{
    public static String getName( Class<?> type )
    {
        String name = getFormalName( type );
        return new StringBuilder().append( Character.toLowerCase( name.charAt( 0 ) ) )
                .append( name.substring( 1 ) ).toString();
    }

    public static String getFormalName( Class<?> type )
    {
        String split[] = type.getName().split( "[.$]" );
        return split[split.length - 1];
    }
}
