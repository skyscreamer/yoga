package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class ListHierarchicalModel extends AbstractHierarchicalModel
{

    List<Object> list = new ArrayList<Object>();

    @Override
    public void addSimple(PropertyDescriptor property, Object value)
    {
        list.add( value );
    }

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
