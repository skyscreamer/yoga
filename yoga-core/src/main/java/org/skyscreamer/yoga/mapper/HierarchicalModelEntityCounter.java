package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.util.EntityCountExceededException;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 3/31/12
 * Time: 5:21 PM
 */
public class HierarchicalModelEntityCounter implements HierarchicalModelObserver {
    private int _count = 0;
    private final int _maxEntities;

    public HierarchicalModelEntityCounter(int maxEntities) {
        _maxEntities = maxEntities;
    }

    @Override
    public void addedSimple(String name, Object value) {
        countAndCheck();
    }

    @Override
    public void createdChild(String name, Object value) {
        countAndCheck();
    }

    @Override
    public void createdList(String name, Object value) {
        countAndCheck();
    }

    private void countAndCheck() {
        ++_count;
        if (_maxEntities > -1 && _count > _maxEntities) {
            throw new EntityCountExceededException("Exceeded maximum limit of " + _maxEntities + " entities " +
                    "in a single model");
        }
    }

    public int getCount() {
        return _count;
    }

    public int getMaxEntities() {
        return _maxEntities;
    }
}
