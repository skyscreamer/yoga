package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;

public interface HierarchicalModel
{
    void addSimple(PropertyDescriptor property, Object value);

    void addSimple(String name, Object result);

    HierarchicalModel createChild(PropertyDescriptor property);

    HierarchicalModel createChild(String property);

    HierarchicalModel createList(PropertyDescriptor property);

    HierarchicalModel createList(String property);
}
