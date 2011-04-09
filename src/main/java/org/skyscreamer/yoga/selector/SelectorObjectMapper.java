package org.skyscreamer.yoga.selector;

import org.codehaus.jackson.map.ObjectMapper;

public class SelectorObjectMapper extends ObjectMapper
{
    public SelectorObjectMapper()
    {
        setSerializerFactory( new SelectorBeanFactory() );
        getSerializationConfig().setSerializationView( String.class );
    }
}
