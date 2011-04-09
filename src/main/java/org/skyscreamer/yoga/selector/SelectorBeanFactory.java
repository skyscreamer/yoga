package org.skyscreamer.yoga.selector;

import java.util.List;

import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;
import org.codehaus.jackson.map.ser.BeanSerializer;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class SelectorBeanFactory extends CustomSerializerFactory
{
    protected BeanSerializer processViews( SerializationConfig config, BasicBeanDescription beanDesc,
            BeanSerializer ser, List<BeanPropertyWriter> props )
    {
        ser = super.processViews( config, beanDesc, ser, props );

        Class<?> clazz = beanDesc.getBeanClass();
        if ( clazz.getGenericSuperclass().equals( AbstractSelectable.class ) )
        {
            BeanPropertyWriter[] writers = props.toArray( new BeanPropertyWriter[props.size()] );
            for (int i = 0; i < writers.length; ++i)
            {
                writers[i] = new SelectorFieldWriter( writers[i] );
            }
            // Important: create new serializer with filtered writers!
            ser = ser.withFiltered( writers );
        }
        return ser;
    }
}

