package org.skyscreamer.yoga.model;

public interface ListHierarchicalModel<T> extends HierarchicalModel<T>
{
    void addValue( Object instance );

    MapHierarchicalModel<?> createChildMap();

}
