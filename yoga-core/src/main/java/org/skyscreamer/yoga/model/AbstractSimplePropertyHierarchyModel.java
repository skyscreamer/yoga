package org.skyscreamer.yoga.model;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;

public abstract class AbstractSimplePropertyHierarchyModel implements HierarchicalModel
{
    @Override
    public void addSimple( String name, Object value )
    {
        throw new YogaRuntimeException( new IllegalAccessException(
                "addSimple should be called with only 1 argument" ) );
    }

    @Override
    public HierarchicalModel createChild( String property )
    {
        throw new YogaRuntimeException( new IllegalAccessException(
                "addSimple should be called with only 1 argument" ) );
    }

    @Override
    public HierarchicalModel createChild()
    {
        throw new YogaRuntimeException( new IllegalAccessException(
                "addSimple should be called with only 1 argument" ) );
    }

    @Override
    public HierarchicalModel createList( String name )
    {
        throw new YogaRuntimeException( new IllegalAccessException(
                "addSimple should be called with only 1 argument" ) );
    }

    @Override
    public HierarchicalModel createSimple( String property )
    {
        throw new YogaRuntimeException( new IllegalAccessException(
                "addSimple should be called with only 1 argument" ) );
    }
}
