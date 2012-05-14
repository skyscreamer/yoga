package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.annotations.PopulationExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public class DefaultFieldPopulatorRegistry implements FieldPopulatorRegistry
{
    private Map<Class<?>, Object> _registry = new HashMap<Class<?>, Object>();

    public DefaultFieldPopulatorRegistry()
    {
    }

    public DefaultFieldPopulatorRegistry( Object... fieldPopulators )
    {
        register( fieldPopulators );
    }

    public DefaultFieldPopulatorRegistry( List<Object> fieldPopulators )
    {
        register( fieldPopulators.toArray() );
    }

    public void register( Object... fieldPopulators )
    {
        for ( Object fieldPopulator : fieldPopulators )
        {
            Class<?> fieldPopulatorClass = fieldPopulator.getClass();

            if ( fieldPopulatorClass.isAnnotationPresent( PopulationExtension.class ) )
            {
                PopulationExtension annotation = fieldPopulatorClass.getAnnotation( PopulationExtension.class );
                Class<?> type = annotation.value();
                if ( type != null )
                {
                    register( type, fieldPopulator );
                    continue;
                }
            }

            // at this point, there should have been a registeration if
            // @PopulatorExtension was set up correctly. If not, throw an
            // exception
            throw new IllegalStateException(
                    "all FieldPopulators require the @PopulationExtension to specify the underlying object that's being populated" );
        }
    }

    public Object register( Class<?> typeToExtend, Object fieldPopulator )
    {
        return _registry.put( typeToExtend, fieldPopulator );
    }

    public Object getFieldPopulator( Class<?> clazz )
    {
        return _registry.get( clazz );
    }
}
