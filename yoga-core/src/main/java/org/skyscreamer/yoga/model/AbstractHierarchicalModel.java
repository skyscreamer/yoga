package org.skyscreamer.yoga.model;


public abstract class AbstractHierarchicalModel<T> implements HierarchicalModel<T>
{

    @Override
    public HierarchicalModel<?> createChild( String name )

    {
        MapHierarchicalModel child = new MapHierarchicalModel();
        createSimple( name ).addSimple( child.getUnderlyingModel() );
        return child;
    }
    
    public HierarchicalModel<?> createChild()
    {
        MapHierarchicalModel child = new MapHierarchicalModel();
        addSimple( child.getUnderlyingModel() );
        return child;
    }

    @Override
    public HierarchicalModel<?> createList( String name )
    {
        ListHierarchicalModel child = new ListHierarchicalModel();
        createSimple( name ).addSimple( child.getUnderlyingModel() );
        return child;
    }

}
