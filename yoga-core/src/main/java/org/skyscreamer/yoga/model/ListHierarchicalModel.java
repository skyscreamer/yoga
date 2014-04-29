package org.skyscreamer.yoga.model;

import java.io.IOException;

public interface ListHierarchicalModel<T> extends HierarchicalModel<T>
{
    void addValue( Object instance ) throws IOException;

    MapHierarchicalModel<?> createChildMap() throws IOException;
}
