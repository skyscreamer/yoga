package org.skyscreamer.yoga.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectListHierarchicalModelImpl implements ListHierarchicalModel<List<?>>
{

    List<Object> _list = new ArrayList<Object>();

    @Override
    public MapHierarchicalModel<?> createChildMap()
    {
        ObjectMapHierarchicalModelImpl child = new ObjectMapHierarchicalModelImpl();
        addValue( child.getUnderlyingModel() );
        return child;
    }

    @Override
    public ListHierarchicalModel<?> createChildList() throws IOException
    {
        ObjectListHierarchicalModelImpl child = new ObjectListHierarchicalModelImpl();
        addValue( child.getUnderlyingModel() );
        return child;
    }

    @Override
    public void addValue( Object instance )
    {
        _list.add( instance );
    }

    public List<?> getUnderlyingModel()
    {
        return _list;
    }

    public void finished()
    {
    }
}
