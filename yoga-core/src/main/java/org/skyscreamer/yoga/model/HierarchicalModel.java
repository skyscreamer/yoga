package org.skyscreamer.yoga.model;


public interface HierarchicalModel
{
    void addSimple(String name, Object result);

    HierarchicalModel createChild(String property);

    HierarchicalModel createList(String property);
}
