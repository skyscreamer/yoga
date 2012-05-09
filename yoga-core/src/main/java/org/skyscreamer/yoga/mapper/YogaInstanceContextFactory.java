package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObservedHierarchicalModel;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.DefaultClassFinderStrategy;

public class YogaInstanceContextFactory
{
    private int _maxEntities = -1;

    private FieldPopulatorRegistry _fieldPopulatorRegistry = new DefaultFieldPopulatorRegistry();

    protected ClassFinderStrategy _classFinderStrategy = new DefaultClassFinderStrategy();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public YogaInstanceContext createEntityContext( Object instance, Selector fieldSelector,
            HierarchicalModel model, YogaRequestContext context )
    {
        Class<?> type = _classFinderStrategy.findClass( instance );

        HierarchicalModel entityModel = model;
        if ( _maxEntities > -1 )
        {
            HierarchicalModelEntityCounter counter = (HierarchicalModelEntityCounter) context.getProperty( "element_counter" );
            if ( counter == null )
            {
                counter = new HierarchicalModelEntityCounter( _maxEntities );
                context.setProperty( "element_counter", counter );
            }
            entityModel = new ObservedHierarchicalModel( model, counter );
        }

        YogaInstanceContext entityContext = new YogaInstanceContext( instance, type, fieldSelector,
                entityModel, context );

        FieldPopulator fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( type );
        entityContext.setPopulator( fieldPopulator );

        return entityContext;
    }

    public void setMaxEntities( int _maxEntities )
    {
        this._maxEntities = _maxEntities;
    }

    public void setFieldPopulatorRegistry( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        _fieldPopulatorRegistry = fieldPopulatorRegistry;
    }

    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        _classFinderStrategy = classFinderStrategy;
    }
}
