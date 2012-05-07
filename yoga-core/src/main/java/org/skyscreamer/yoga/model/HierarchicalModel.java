package org.skyscreamer.yoga.model;

public interface HierarchicalModel
{
    void addSimple(String name, Object result);
    void addSimple(Object instance);

    HierarchicalModel createChild(String name);
    HierarchicalModel createChild();

    HierarchicalModel createList(String name);
    
    HierarchicalModel createSimple(String name);


}
