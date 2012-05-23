package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.annotations.CoreFields;
import org.skyscreamer.yoga.metadata.PropertyUtil;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;

public class CoreSelector extends MapSelector
{
    private FieldPopulatorRegistry _fieldPopulatorRegistry;

    public CoreSelector( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        _fieldPopulatorRegistry = fieldPopulatorRegistry;
    }

    @Override
    protected Set<String> getFieldCollection( Class<?> instanceType )
    {
        Set<String> fields = descriptors.get( instanceType );
        if (fields == null)
        {
            descriptors.put( instanceType, fields = createFields( instanceType ) );
        }
        return fields;
    }

    @SuppressWarnings("unchecked")
    private Set<String> createFields( Class<?> instanceType )
    {
        Set<String> response = new TreeSet<String>();
        List<PropertyDescriptor> readableProperties = PropertyUtil.getReadableProperties( instanceType );

        for (PropertyDescriptor property : readableProperties)
        {
            Method readMethod = property.getReadMethod();
            if (readMethod.isAnnotationPresent( Core.class ))
            {
                response.add( property.getName() );
            }
        }
        Object fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( instanceType );
        if ( fieldPopulator != null )
        {
            try
            {
                for ( Method method : fieldPopulator.getClass().getMethods() )
                {
                    if ( method.isAnnotationPresent( CoreFields.class ) )
                    {
                        List<String> coreFields = (List<String>) method.invoke( fieldPopulator );
                        for ( String coreField : coreFields )
                        {
                            if ( PropertyUtil.propertiesInclude( readableProperties, coreField ) )
                            {
                                response.add( coreField );
                            }
                        }
                        break;
                    }
                }
            }
            catch ( Exception e )
            {
                // @CoreFields not found on method of return type List<String>
            }
        }
        return response;
    }
    
    @Override
    public Set<String> getAllPossibleFields( Class<?> instanceType )
    {
        Set<String> response = new TreeSet<String>();
        List<PropertyDescriptor> readableProperties = PropertyUtil.getReadableProperties( instanceType );

        for (PropertyDescriptor property : readableProperties)
        {
            response.add( property.getName() );
        }
        return response;
    }

}
