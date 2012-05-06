package org.skyscreamer.yoga.model;



public abstract class AbstractHierarchicalModel implements HierarchicalModel
{

    @Override
    public HierarchicalModel createChild(String name)
    {
        MapHierarchicalModel child = new MapHierarchicalModel();
        addSimple( name, child.getObjectTree() );
        return child;
    }
    
    public HierarchicalModel createChild()
    {
        MapHierarchicalModel child = new MapHierarchicalModel();
        addSimple( child.getObjectTree() );
        return child;
    }

    @Override
    public HierarchicalModel createList(String name)
    {
        ListHierarchicalModel child = new ListHierarchicalModel();
        addSimple( name, child.getList() );
        return child;
    }
    
}
