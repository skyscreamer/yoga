package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObservedHierarchicalModel;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.ClassFinderStrategy;

public class YogaInstanceContextFactory
{
    private int _maxEntities = -1;

    private FieldPopulatorRegistry _fieldPopulatorRegistry = new DefaultFieldPopulatorRegistry();

    protected ClassFinderStrategy _classFinderStrategy;

    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public YogaInstanceContext createEntityContext(Object instance, Selector fieldSelector,
            HierarchicalModel model, YogaRequestContext context)
    {
        Class<?> type = findClass( instance );
        YogaInstanceContext entityContext = new YogaInstanceContext( instance, type, fieldSelector,
                model, context );

        if (_maxEntities > -1)
        {
            model = new ObservedHierarchicalModel( model, new HierarchicalModelEntityCounter(
                    context, _maxEntities ) );
        }

        FieldPopulator fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( entityContext
                .getInstanceType() );
        entityContext.setPopulator( fieldPopulator );

        return entityContext;
    }

    public int getMaxEntities()
    {
        return _maxEntities;
    }

    public void setMaxEntities(int _maxEntities)
    {
        this._maxEntities = _maxEntities;
    }

    public void setFieldPopulatorRegistry(FieldPopulatorRegistry fieldPopulatorRegistry)
    {
        _fieldPopulatorRegistry = fieldPopulatorRegistry;
    }

    public Class<?> findClass(Object instance)
    {
        return _classFinderStrategy.findClass( instance );
    }

    public void setClassFinderStrategy(ClassFinderStrategy classFinderStrategy)
    {
        _classFinderStrategy = classFinderStrategy;
    }
}
