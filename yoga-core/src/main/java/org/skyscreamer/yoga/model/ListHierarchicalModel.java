package org.skyscreamer.yoga.model;

import java.util.ArrayList;
import java.util.List;

public class ListHierarchicalModel extends AbstractHierarchicalModel
{

    List<Object> list = new ArrayList<Object>();

    @Override
    public void addSimple(String name, Object value)
    {
        list.add( value );
    }

    public List<Object> getList()
    {
        return list;
    }

}
