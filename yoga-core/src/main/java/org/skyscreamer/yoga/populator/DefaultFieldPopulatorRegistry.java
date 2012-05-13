package org.skyscreamer.yoga.populator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skyscreamer.yoga.annotations.PopulationExtension;

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
        register( Arrays.asList( fieldPopulators ) );
    }

    public DefaultFieldPopulatorRegistry( List<Object> fieldPopulators )
    {
        register( fieldPopulators );
    }

    public void register( List<Object> fieldPopulators )
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
                    break;
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
