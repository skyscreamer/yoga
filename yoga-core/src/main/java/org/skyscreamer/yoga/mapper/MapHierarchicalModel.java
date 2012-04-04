package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class MapHierarchicalModel extends AbstractHierarchicalModel
{
    Map<String, Object> _objectTree = new HashMap<String, Object>();

    public MapHierarchicalModel()
    {
    }

    public MapHierarchicalModel(Map<String, Object> objectTree)
    {
        _objectTree = objectTree;
    }

    @Override
    public void addSimple(PropertyDescriptor property, Object value)
    {
        _objectTree.put( property.getName(), value );
    }

    @Override
    public void addSimple(String name, Object value)
    {
        _objectTree.put( name, value );
    }

    public Map<String, Object> getObjectTree()
    {
        return _objectTree;
    }
}
