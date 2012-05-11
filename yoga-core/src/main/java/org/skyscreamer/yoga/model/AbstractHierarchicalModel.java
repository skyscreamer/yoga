package org.skyscreamer.yoga.model;


public abstract class AbstractHierarchicalModel<T> implements HierarchicalModel<T>
{

    @Override
    public HierarchicalModel<?> createChildMap( String name )

    {
        MapHierarchicalModel child = new MapHierarchicalModel();
        addProperty( name, child.getUnderlyingModel() );
        return child;
    }
    
    public HierarchicalModel<?> createChildMap()
    {
        MapHierarchicalModel child = new MapHierarchicalModel();
        addValue( child.getUnderlyingModel() );
        return child;
    }

    @Override
    public HierarchicalModel<?> createChildList( String name )
    {
        ListHierarchicalModel child = new ListHierarchicalModel();
        addProperty( name, child.getUnderlyingModel() );
        return child;
    }

}
