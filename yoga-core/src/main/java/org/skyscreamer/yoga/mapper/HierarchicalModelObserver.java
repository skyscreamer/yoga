package org.skyscreamer.yoga.mapper;

public interface HierarchicalModelObserver {
    void addedSimple(String name, Object value);
    void createdChild(String name, Object value);
    void createdList(String name, Object value);
}
