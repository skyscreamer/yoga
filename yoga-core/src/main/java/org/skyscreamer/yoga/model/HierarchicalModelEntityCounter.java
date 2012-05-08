package org.skyscreamer.yoga.model;

import java.util.concurrent.atomic.AtomicInteger;

import org.skyscreamer.yoga.exceptions.EntityCountExceededException;

/**
 * creating by IntelliJ IDEA. User: Carter Page Date: 3/31/12 Time: 5:21 PM
 */
public class HierarchicalModelEntityCounter implements HierarchicalModelObserver
{
    private final int _maxEntities;
    private AtomicInteger counter = new AtomicInteger();

    public HierarchicalModelEntityCounter( int maxEntities )
    {
        _maxEntities = maxEntities;
    }

    @Override
    public void creatingChild( String name, HierarchicalModel<?> model )
    {
        countAndCheck();
    }

    @Override
    public void creatingList( String name, HierarchicalModel<?> model )
    {
        countAndCheck();
    }

    private void countAndCheck()
    {
        int currentCount = counter.incrementAndGet();
        if (_maxEntities > -1 && currentCount > _maxEntities)
        {
            throw new EntityCountExceededException( "Exceeded maximum limit of " + _maxEntities
                    + " entities in a single model" );
        }
    }

    public int getMaxEntities()
    {
        return _maxEntities;
    }

    @Override
    public void addingSimple( String name, Object value, HierarchicalModel<?> model )
    {
    }

    @Override
    public void addingSimple( Object value, HierarchicalModel<?> model )
    {
    }

    @Override
    public void creatingSimple( String name, HierarchicalModel<?> model )
    {
    }

    @Override
    public void creatingChild( HierarchicalModel<?> _hierHierarchicalModel )
    {
    }
}
