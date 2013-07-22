package org.skyscreamer.yoga.util;

/**
 * Basic utilities for creating a pretty name for a class.
 * 
 * @author solomon.duskis
 *
 */
public class NameUtil
{
    public static String getName( Class<?> type )
    {
        String name = getFormalName( type );
        return String.valueOf( Character.toLowerCase( name.charAt( 0 ) ) ) + name.substring( 1 );
    }

    public static String getFormalName( Class<?> type )
    {
        String split[] = type.getName().split( "[.$]" );
        return split[split.length - 1];
    }
}
