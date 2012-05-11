package org.skyscreamer.yoga.model;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class MapHierarchicalModel extends AbstractHierarchicalModel<Map<String, Object>>
{
    final Map<String, Object> _objectTree;

    public MapHierarchicalModel()
    {
        _objectTree = new HashMap<String, Object>();
    }

    public MapHierarchicalModel( Map<String, Object> objectTree )
    {
        _objectTree = objectTree;
    }

    @Override
    public void addProperty( String name, Object value )
    {
        _objectTree.put( name, value );
    }

    @Override
    public HierarchicalModel<?> createSimple( String name )
    {
        return new SimplePropertyHierarchyModel( name, this );
    }

    @Override
    public void addValue( Object instance )
    {
        throw new YogaRuntimeException( new IllegalAccessException(
                "addSimple with a single value is not supported" ) );
    }

    public Map<String, Object> getUnderlyingModel()
    {
        return _objectTree;
    }
}
