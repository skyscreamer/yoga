package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;

/**
 * Wrapper class that allows a model to be observed as it is constructed.
 */
public class ObservedHierarchicalModel implements HierarchicalModel
{
    private final HierarchicalModel _hierHierarchicalModel;
    private final HierarchicalModelObserver[] _observers;

    public ObservedHierarchicalModel( HierarchicalModel hierarchicalModel, HierarchicalModelObserver... observers )
    {
        _hierHierarchicalModel = hierarchicalModel;
        for ( HierarchicalModelObserver observer : observers )
        {
            if ( observer == null )
            {
                throw new NullPointerException( "Null observer not allowed" );
            }
        }
        _observers = observers;
    }

    @Override
    public void addSimple( PropertyDescriptor property, Object value )
    {
        _hierHierarchicalModel.addSimple( property, value );
        for ( HierarchicalModelObserver observer : _observers )
        {
            observer.addedSimple( property.getName(), value );
        }
    }

    @Override
    public void addSimple( String name, Object value )
    {
        _hierHierarchicalModel.addSimple( name, value );
        for ( HierarchicalModelObserver observer : _observers )
        {
            observer.addedSimple( name, value );
        }
    }

    @Override
    public HierarchicalModel createChild( PropertyDescriptor property )
    {
        for ( HierarchicalModelObserver observer : _observers )
        {
            observer.createdChild( property.getName() );
        }
        return new ObservedHierarchicalModel( _hierHierarchicalModel.createChild( property ), _observers );
    }

    @Override
    public HierarchicalModel createChild( String name )
    {
        for ( HierarchicalModelObserver observer : _observers )
        {
            observer.createdChild( name );
        }
        return new ObservedHierarchicalModel( _hierHierarchicalModel.createChild( name ), _observers );
    }

    @Override
    public HierarchicalModel createList( PropertyDescriptor property )
    {
        for ( HierarchicalModelObserver observer : _observers )
        {
            observer.createdList( property.getName() );
        }
        return new ObservedHierarchicalModel( _hierHierarchicalModel.createList( property ), _observers );
    }

    @Override
    public HierarchicalModel createList( String name )
    {
        for ( HierarchicalModelObserver observer : _observers )
        {
            observer.createdList( name );
        }
        return new ObservedHierarchicalModel( _hierHierarchicalModel.createList( name ), _observers );
    }
}
