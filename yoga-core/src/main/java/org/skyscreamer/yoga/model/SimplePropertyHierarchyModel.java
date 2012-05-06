package org.skyscreamer.yoga.model;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public class SimplePropertyHierarchyModel extends AbstractSimplePropertyHierarchyModel
{
    private HierarchicalModel underlying;
    private String name;

    public SimplePropertyHierarchyModel(String name, HierarchicalModel underlying)
    {
        if (name == null || underlying == null)
        {
            throw new YogaRuntimeException( new IllegalAccessException(
                    "name and underlying were not set" ) );
        }
        this.underlying = underlying;
        this.name = name;
    }

    @Override
    public void addSimple(Object value)
    {
        underlying.addSimple( name, value );
    }
}
