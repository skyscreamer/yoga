package org.skyscreamer.yoga.demo.test;

import java.util.HashMap;
import java.util.Map;

public class MapBeanConext implements BeanContext
{
    Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

    public <T> void register( Class<T> type, T o )
    {
        map.put( type, o );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <T> T getBean( Class<T> type )
    {
        return (T) map.get( type );
    }

}
