package org.skyscreamer.yoga.model;


public interface HierarchicalModelObserver
{
    void addingSimple(Object value, HierarchicalModel<?> model);

    void addingSimple(String name, Object value, HierarchicalModel<?> _hierHierarchicalModel);

    void creatingSimple(String name, HierarchicalModel<?> model);

    void creatingChild(String name, HierarchicalModel<?> model);

    void creatingList(String name, HierarchicalModel<?> model);

    void creatingChild(HierarchicalModel<?> _hierHierarchicalModel);


}
