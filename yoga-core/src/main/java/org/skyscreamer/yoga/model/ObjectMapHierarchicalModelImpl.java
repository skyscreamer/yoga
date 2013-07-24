package org.skyscreamer.yoga.model;

import java.util.HashMap;
import java.util.Map;

public class ObjectMapHierarchicalModelImpl implements MapHierarchicalModel<Map<String, Object>>
{
    final Map<String, Object> _objectTree;

    public ObjectMapHierarchicalModelImpl()
    {
        _objectTree = new HashMap<String, Object>();
    }

    public ObjectMapHierarchicalModelImpl( Map<String, Object> objectTree )
    {
        _objectTree = objectTree;
    }

    @Override
    public MapHierarchicalModel<?> createChildMap( String name )
    {
        ObjectMapHierarchicalModelImpl child = new ObjectMapHierarchicalModelImpl();
        _objectTree.put( name, child.getUnderlyingModel() );
        return child;
    }

    @Override
    public ListHierarchicalModel<?> createChildList( String name )
    {
        ObjectListHierarchicalModelImpl child = new ObjectListHierarchicalModelImpl();
        _objectTree.put( name, child.getUnderlyingModel() );
        return child;
    }

    @Override
    public void addProperty( String name, Object value )
    {
        _objectTree.put( name, value );
    }

    public Map<String, Object> getUnderlyingModel()
    {
        return _objectTree;
    }

    public void finished()
    {
    }
}