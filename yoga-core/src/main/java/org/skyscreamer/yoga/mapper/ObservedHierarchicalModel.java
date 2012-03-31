package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * Wrapper class that allows a model to be observed as it is constructed.
 */
public class ObservedHierarchicalModel implements HierarchicalModel {
    private final HierarchicalModel _hierHierarchicalModel;
    private final HierarchicalModelObserver[] _observers;

    public ObservedHierarchicalModel(HierarchicalModel hierarchicalModel, HierarchicalModelObserver... observers) {
        _hierHierarchicalModel = hierarchicalModel;
        for(HierarchicalModelObserver observer : observers) {
            if (observer == null) {
                throw new NullPointerException("Null observer not allowed");
            }
        }
        _observers = observers;
    }

    @Override
    public void addSimple(PropertyDescriptor property, Object value) {
        _hierHierarchicalModel.addSimple(property, value);
        for(HierarchicalModelObserver observer : _observers) {
            observer.addedSimple(property.getName(), value);
        }
    }

    @Override
    public void addSimple(String name, Object value) {
        _hierHierarchicalModel.addSimple(name, value);
        for(HierarchicalModelObserver observer : _observers) {
            observer.addedSimple(name, value);
        }
    }

    @Override
    public HierarchicalModel createChild(PropertyDescriptor property, Object value) {
        for(HierarchicalModelObserver observer : _observers) {
            observer.createdChild(property.getName(), value);
        }
        return new ObservedHierarchicalModel(_hierHierarchicalModel.createChild(property, value), _observers);
    }

    @Override
    public HierarchicalModel createChild(String name, Object value) {
        for(HierarchicalModelObserver observer : _observers) {
            observer.createdChild(name, value);
        }
        return new ObservedHierarchicalModel(_hierHierarchicalModel.createChild(name, value), _observers);
    }

    @Override
    public HierarchicalModel createList(PropertyDescriptor property, Object value) {
        for(HierarchicalModelObserver observer : _observers) {
            observer.createdList(property.getName(), value);
        }
        return new ObservedHierarchicalModel(_hierHierarchicalModel.createList(property, value), _observers);
    }

    @Override
    public HierarchicalModel createList(String name, Object value) {
        for(HierarchicalModelObserver observer : _observers) {
            observer.createdList(name, value);
        }
        return new ObservedHierarchicalModel(_hierHierarchicalModel.createList(name, value), _observers);
    }
}
