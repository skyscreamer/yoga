package org.skyscreamer.yoga.model;

import java.io.IOException;

public interface HierarchicalModel<T>
{
    T getUnderlyingModel();

    void finished() throws IOException;

}
