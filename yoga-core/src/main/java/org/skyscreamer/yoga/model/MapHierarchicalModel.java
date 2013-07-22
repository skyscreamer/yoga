package org.skyscreamer.yoga.model;

import java.io.IOException;

public interface MapHierarchicalModel<T> extends HierarchicalModel<T>
{
    void addProperty( String name, Object result ) throws IOException;

    MapHierarchicalModel<?> createChildMap( String name ) throws IOException;

    ListHierarchicalModel<?> createChildList( String name ) throws IOException;
}
