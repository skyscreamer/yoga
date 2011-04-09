package org.skyscreamer.yoga.selector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;

public class SelectorFieldWriter extends BeanPropertyWriter
{
    final BeanPropertyWriter _writer;

    public SelectorFieldWriter( BeanPropertyWriter writer )
    {
        super( writer );
        _writer = writer;
    }

    public void serializeAsField( Object bean, JsonGenerator jgen, SerializerProvider prov ) throws Exception
    {
        Selectable selectable = (Selectable)bean;
        Set<String> selectedFields = computeSelectedFields( selectable );
        if ( isCore( _accessorMethod ) || selectedFields.contains( _name ) )
        {
            jgen.writeObjectField( _name, _accessorMethod.invoke( bean, new Object[0] ) );
        }
    }

    private Set<String> computeSelectedFields( Selectable selectable )
    {
        Set<String> result = new HashSet<String>();
        for( SelectorField selectorField : selectable.getSelectorFields() )
        {
            result.add( selectorField.getFieldName() );
        }
        return result;
    }

    private boolean isCore( Method accessorMethod )
    {
        boolean result = false;
        for( Annotation annotation : accessorMethod.getDeclaredAnnotations() )
        {
            if ( annotation instanceof Core )
            {
                result = true;
            }
        }
        return result;
    }
}
