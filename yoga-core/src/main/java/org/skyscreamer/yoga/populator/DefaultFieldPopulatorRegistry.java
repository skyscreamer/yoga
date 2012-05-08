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
    private Map<Class<?>, FieldPopulator> _registry = new HashMap<Class<?>, FieldPopulator>();

    public DefaultFieldPopulatorRegistry()
    {

    }

    public DefaultFieldPopulatorRegistry( List<FieldPopulator> fieldPopulators )
    {
        register( fieldPopulators );
    }

    public void register( List<FieldPopulator> fieldPopulators )
    {
        for ( FieldPopulator fieldPopulator : fieldPopulators )
        {
            Class<? extends FieldPopulator> fieldPopulatorClass = fieldPopulator.getClass();

            if ( fieldPopulatorClass.isAnnotationPresent( PopulationExtension.class ) )
            {
                PopulationExtension annotation = fieldPopulatorClass
                        .getAnnotation( PopulationExtension.class );
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

    public FieldPopulator register( Class<?> typeToExtend, FieldPopulator fieldPopulator )
    {
        return _registry.put( typeToExtend, fieldPopulator );
    }

    public FieldPopulator getFieldPopulator( Class<?> clazz )
    {
        return _registry.get( clazz );
    }
}
