package org.skyscreamer.yoga.populator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public class DefaultFieldPopulatorRegistry implements FieldPopulatorRegistry
{
    private Map<Class<?>, FieldPopulator<?>> _registry = new HashMap<Class<?>, FieldPopulator<?>>();

    @SuppressWarnings("rawtypes")
    public DefaultFieldPopulatorRegistry(List<FieldPopulator<?>> fieldPopulators)
    {
        for (FieldPopulator<?> fieldPopulator : fieldPopulators)
        {
            Class<? extends FieldPopulator> fieldPopulatorClass = fieldPopulator.getClass();
            for (Type genericInterface : fieldPopulatorClass.getGenericInterfaces())
            {
                if (genericInterface.toString().contains( FieldPopulator.class.getName() ))
                {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                    Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    _registry.put( clazz, fieldPopulator );
                    break;
                }
            }
            Type genericSuperclass = fieldPopulatorClass.getGenericSuperclass();
            if (genericSuperclass.toString().contains( FieldPopulatorSupport.class.getName() ))
            {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                _registry.put( clazz, fieldPopulator );
                break;
            }
        }
    }

    public FieldPopulator<?> getFieldPopulator(Class<?> clazz)
    {
        return _registry.get( clazz );
    }
}
