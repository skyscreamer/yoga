package org.skyscreamer.yoga.model;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class ListHierarchicalModel extends AbstractHierarchicalModel<List<?>>
{

    List<Object> list = new ArrayList<Object>();

    @Override
    public void addProperty( String name, Object value )
    {
        throw new YogaRuntimeException( new IllegalAccessError(
                "you shouldn't be calling this method for a list" ) );
    }

    @Override
    public void addValue( Object instance )
    {
        list.add( instance );
    }

    public List<?> getUnderlyingModel()
    {
        return list;
    }

    @Override
    public HierarchicalModel<?> createSimple( String name )
    {
        return this;
    }
}
