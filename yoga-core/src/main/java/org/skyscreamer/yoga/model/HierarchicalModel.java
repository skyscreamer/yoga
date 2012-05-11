package org.skyscreamer.yoga.model;

public interface HierarchicalModel<T>
{

    void addProperty( String name, Object result );

    void addValue( Object instance );

    HierarchicalModel<?> createChildMap( String name );

    HierarchicalModel<?> createChildMap();

    HierarchicalModel<?> createChildList( String name );

    HierarchicalModel<?> createSimple( String name );


    T getUnderlyingModel();
}
