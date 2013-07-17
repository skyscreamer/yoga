package org.skyscreamer.yoga.model;

public interface MapHierarchicalModel<T> extends HierarchicalModel<T>
{

    void addProperty( String name, Object result );

    MapHierarchicalModel<?> createChildMap( String name );

    ListHierarchicalModel<?> createChildList( String name );

}
