package org.skyscreamer.yoga.mapper;

import static org.skyscreamer.yoga.metadata.PropertyUtil.getReadableProperties;
import static org.skyscreamer.yoga.util.ObjectUtil.isPrimitive;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.enricher.Enricher;
import org.skyscreamer.yoga.enricher.HrefEnricher;
import org.skyscreamer.yoga.enricher.ModelDefinitionBuilder;
import org.skyscreamer.yoga.enricher.NavigationLinksEnricher;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class ResultTraverser
{
    private static final List<Enricher> DEFAULT_ENRICHERS = Arrays.asList( new HrefEnricher(),
            new ModelDefinitionBuilder(), new NavigationLinksEnricher() );

    private List<Enricher> _enrichers = DEFAULT_ENRICHERS;

    private YogaInstanceContextFactory instanceContextFactory;

    @SuppressWarnings("rawtypes")
    public void traverse(Object instance, Selector fieldSelector, HierarchicalModel model,
            YogaRequestContext context)
    {
        YogaInstanceContext entityContext = instanceContextFactory.createEntityContext( 
                instance, fieldSelector, model, context );

        for (Enricher enricher : _enrichers)
        {
            enricher.enrich( entityContext );
        }

        addInstanceFields( entityContext );
        addPopulatorExtraFields( entityContext );
    }

    protected void addInstanceFields(YogaInstanceContext<?> entityContext)
    {
        for (PropertyDescriptor property : getReadableProperties( entityContext.getInstanceType() ))
        {
            if (entityContext.containsInstanceField( property ) )
            {
                String propertyName = property.getName();
                Object fieldValue = entityContext.getInstanceFieldValue( propertyName );
                addChild( propertyName, fieldValue, entityContext );
            }
        }
    }
    
    protected void addPopulatorExtraFields(YogaInstanceContext<?> entityContext)
    {
        for (Method method : entityContext.getPopulatorExtraFieldMethods())
        {
            String propertyName = method.getAnnotation( ExtraField.class ).value();
            Object fieldValue = entityContext.getPopulatorFieldValue( method );
            addChild( propertyName, fieldValue, entityContext );
        }
    }


    protected void addChild(String fieldName, Object fieldValue,
            YogaInstanceContext<?> entityContext)
    {
        if (fieldValue == null)
        {
            return;
        }

        Selector childSelector = entityContext.getFieldSelector().getField( fieldName );

        Class<?> propertyClassType = fieldValue.getClass();
        HierarchicalModel model = entityContext.getModel();

        if (fieldValue != null && isPrimitive( propertyClassType ))
        {
            model.addSimple( fieldName, fieldValue );
        }
        else if (Iterable.class.isAssignableFrom( propertyClassType ))
        {
            traverseIterable( childSelector, model, fieldName, (Iterable<?>) fieldValue,
                    entityContext.getRequestContext() );
        }
        else
        {
            traverse( fieldValue, childSelector, model.createChild( fieldName ),
                    entityContext.getRequestContext() );
        }
    }

    protected void traverseIterable(Selector fieldSelector, HierarchicalModel model,
            String fieldName, Iterable<?> iterable, YogaRequestContext context)
    {
        if (iterable == null)
            return;

        HierarchicalModel listModel = model.createList( fieldName );

        for (Object o : iterable)
        {
            if (isPrimitive( instanceContextFactory.findClass( o ) ))
            {
                listModel.addSimple( fieldName, o );
            }
            else
            {
                traverse( o, fieldSelector.getField( fieldName ),
                        listModel.createChild( fieldName ), context );
            }
        }
    }

    public void setEnrichers(List<Enricher> enrichers)
    {
        this._enrichers = enrichers;
    }

    public void setInstanceContextFactory(YogaInstanceContextFactory instanceContextFactory)
    {
        this.instanceContextFactory = instanceContextFactory;
    }
}
