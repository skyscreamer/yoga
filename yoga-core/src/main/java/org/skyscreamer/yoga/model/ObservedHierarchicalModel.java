package org.skyscreamer.yoga.model;


/**
 * Wrapper class that allows a model to be observed as it is constructed.
 */
public class ObservedHierarchicalModel implements HierarchicalModel
{
    private final HierarchicalModel _hierHierarchicalModel;
    private final HierarchicalModelObserver[] _observers;

    public ObservedHierarchicalModel(HierarchicalModel hierarchicalModel,
            HierarchicalModelObserver... observers)
    {
        _hierHierarchicalModel = hierarchicalModel;
        for (HierarchicalModelObserver observer : observers)
        {
            if (observer == null)
            {
                throw new NullPointerException( "Null observer not allowed" );
            }
        }
        _observers = observers;
    }


    @Override
    public HierarchicalModel createChild(String name)
    {
        for (HierarchicalModelObserver observer : _observers)
        {
            observer.creatingChild( name, _hierHierarchicalModel );
        }
        HierarchicalModel child = _hierHierarchicalModel.createChild( name );
        return wrap( child );
    }

    public HierarchicalModel wrap(HierarchicalModel child)
    {
        return child instanceof ObservedHierarchicalModel ? child : new ObservedHierarchicalModel( child,
                _observers );
    }

    @Override
    public HierarchicalModel createChild()
    {
        for (HierarchicalModelObserver observer : _observers)
        {
            observer.creatingChild( _hierHierarchicalModel );
        }
        return wrap( _hierHierarchicalModel.createChild() );

    }

    @Override
    public HierarchicalModel createList(String name)
    {
        for (HierarchicalModelObserver observer : _observers)
        {
            observer.creatingList( name, _hierHierarchicalModel );
        }
        return wrap( _hierHierarchicalModel.createList( name ) );
    }

    @Override
    public void addSimple(Object instance)
    {
        for (HierarchicalModelObserver observer : _observers)
        {
            observer.addingSimple( instance, _hierHierarchicalModel );
        }
        _hierHierarchicalModel.addSimple( instance );
    }
    
    @Override
    public void addSimple(String name, Object value)
    {
        for (HierarchicalModelObserver observer : _observers)
        {
            observer.addingSimple( name, value, _hierHierarchicalModel );
        }
        _hierHierarchicalModel.addSimple( name, value );
    }

    @Override
    public HierarchicalModel createSimple(String name)
    {
        for (HierarchicalModelObserver observer : _observers)
        {
            observer.creatingSimple( name, _hierHierarchicalModel );
        }
        return wrap( _hierHierarchicalModel.createSimple( name ) );
    }
}
