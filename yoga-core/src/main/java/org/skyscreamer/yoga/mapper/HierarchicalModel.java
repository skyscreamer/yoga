package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;

public interface HierarchicalModel {
    void addSimple(PropertyDescriptor property, Object value);

    void addSimple(String name, Object result);

    // TODO: Do we really need the value argument for createChild and createList?
    // Only place I see it used is XhtmlHeirarchyModel, and doesn't seem necessary there.  Seems YAGNI.
    HierarchicalModel createChild(PropertyDescriptor property, Object value);

    HierarchicalModel createChild(String property, Object value);

    HierarchicalModel createList(PropertyDescriptor property, Object value);

    HierarchicalModel createList(String property, Object value);
}
