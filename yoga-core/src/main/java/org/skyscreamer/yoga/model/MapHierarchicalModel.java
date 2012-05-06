package org.skyscreamer.yoga.model;

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
    public void addSimple(String name, Object value)
    {
        _objectTree.put( name, value );
    }

    public Map<String, Object> getObjectTree()
    {
        return _objectTree;
    }
}
