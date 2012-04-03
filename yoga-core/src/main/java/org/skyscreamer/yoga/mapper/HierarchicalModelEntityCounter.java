package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.util.EntityCountExceededException;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 3/31/12
 * Time: 5:21 PM
 */
public class HierarchicalModelEntityCounter implements HierarchicalModelObserver {
    private final ResultTraverserContext _context;
    private final int _maxEntities;

    public HierarchicalModelEntityCounter(ResultTraverserContext context, int maxEntities) {
        _context = context;
        _maxEntities = maxEntities;
    }

    @Override
    public void addedSimple(String name, Object value) {
//        countAndCheck();
    }

    @Override
    public void createdChild(String name) {
        countAndCheck();
    }

    @Override
    public void createdList(String name) {
//        countAndCheck();
    }

    private void countAndCheck() {
        _context.incrementCounter();
        if (_maxEntities > -1 && _context.readCounter() > _maxEntities) {
            throw new EntityCountExceededException("Exceeded maximum limit of " + _maxEntities + " entities " +
                    "in a single model");
        }
    }

    public int getCount() {
        return _context.readCounter();
    }

    public int getMaxEntities() {
        return _maxEntities;
    }
}
