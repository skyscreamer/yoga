package org.skyscreamer.yoga.mapper;

public interface HierarchicalModelObserver {
    void addedSimple(String name, Object value);
    void createdChild(String name);
    void createdList(String name);
}
