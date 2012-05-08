package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.HierarchicalModelEntityCounter;
import org.skyscreamer.yoga.model.ObservedHierarchicalModel;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.ClassFinderStrategy;

public class YogaInstanceContextFactory
{
    private Integer _maxEntities;

    private FieldPopulatorRegistry _fieldPopulatorRegistry = new DefaultFieldPopulatorRegistry();

    protected ClassFinderStrategy _classFinderStrategy;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public YogaInstanceContext createEntityContext(Object instance, Selector fieldSelector,
            HierarchicalModel<?> model, YogaRequestContext context)
    {
        Class<?> type = findClass( instance );

        HierarchicalModel<?> entityModel = getEntityModel( model, context );

        YogaInstanceContext entityContext = new YogaInstanceContext( instance, type, fieldSelector, entityModel, context );
        entityContext.setPopulator( _fieldPopulatorRegistry.getFieldPopulator( type ) );

        return entityContext;
    }

    protected <T> HierarchicalModel<T> getEntityModel(HierarchicalModel<T> model, YogaRequestContext context)
    {
        if (_maxEntities == null)
        {
            return model;
        }
        else
        {
            HierarchicalModelEntityCounter counter = getCounter( context );
            return new ObservedHierarchicalModel<T>( model, counter );
        }
    }

    protected HierarchicalModelEntityCounter getCounter(YogaRequestContext context)
    {
        HierarchicalModelEntityCounter counter = (HierarchicalModelEntityCounter) context.getProperty("element_counter");
        if(counter == null){
            counter = new HierarchicalModelEntityCounter( _maxEntities );
            context.setProperty( "element_counter", counter );
        }
        return counter;
    }

    // GETTERS AND SETTERS

    public Integer getMaxEntities()
    {
        return _maxEntities;
    }

    public void setMaxEntities(Integer _maxEntities)
    {
        if (_maxEntities != null && _maxEntities < 1)
        {
            throw new YogaRuntimeException( "Cannot have " + _maxEntities
                    + " entities as a maximum" );
        }
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
