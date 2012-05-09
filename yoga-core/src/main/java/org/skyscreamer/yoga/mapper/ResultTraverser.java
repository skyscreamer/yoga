package org.skyscreamer.yoga.mapper;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.enricher.Enricher;
import org.skyscreamer.yoga.enricher.HrefEnricher;
import org.skyscreamer.yoga.enricher.ModelDefinitionBuilder;
import org.skyscreamer.yoga.enricher.NavigationLinksEnricher;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.skyscreamer.yoga.metadata.PropertyUtil.getReadableProperties;
import static org.skyscreamer.yoga.util.ObjectUtil.isPrimitive;

public class ResultTraverser
{
    private static final List<Enricher> DEFAULT_ENRICHERS = Arrays.asList( new HrefEnricher(),
            new ModelDefinitionBuilder(), new NavigationLinksEnricher() );

    private List<Enricher> _enrichers = DEFAULT_ENRICHERS;

    private YogaInstanceContextFactory instanceContextFactory;

    public void traverse( Object instance, Selector fieldSelector, HierarchicalModel model,
            YogaRequestContext context )
    {
        if ( instance != null )
        {
            YogaInstanceContext<?> entity = instanceContextFactory.createEntityContext( instance,
                    fieldSelector, model, context );
            traverse( entity );
        }
    }

    private void traverse( YogaInstanceContext<?> entityContext )
    {
        Class<?> instanceType = entityContext.getInstanceType();
        if ( Iterable.class.isAssignableFrom( instanceType ) )
        {
            YogaRequestContext requestContext = entityContext.getRequestContext();
            Selector fieldSelector = entityContext.getFieldSelector();
            for ( Object o : (Iterable<?>) entityContext.getInstance() )
            {
                if ( isPrimitive( o.getClass() ) )
                {
                    entityContext.getModel().addSimple( o );
                }
                else
                {
                    HierarchicalModel childModel = entityContext.getModel().createChild();
                    traverse( o, fieldSelector, childModel, requestContext );
                }
            }
        }
        else
        {
            for ( Enricher enricher : _enrichers )
            {
                enricher.enrich( entityContext );
            }

            addInstanceFields( entityContext );
            addPopulatorExtraFields( entityContext );
        }
    }

    protected void addInstanceFields( YogaInstanceContext<?> entityContext )
    {
        List<PropertyDescriptor> readableProperties = getReadableProperties( entityContext
                .getInstanceType() );

        for ( PropertyDescriptor property : readableProperties )
        {
            if ( entityContext.containsInstanceField( property ) )
            {
                String propertyName = property.getName();
                Object fieldValue = entityContext.getInstanceFieldValue( propertyName );
                addChild( propertyName, fieldValue, entityContext );
            }
        }
    }

    protected void addPopulatorExtraFields( YogaInstanceContext<?> entityContext )
    {
        List<Method> populatorExtraFieldMethods = entityContext.getPopulatorExtraFieldMethods();

        for ( Method method : populatorExtraFieldMethods )
        {
            String propertyName = method.getAnnotation( ExtraField.class ).value();
            Object fieldValue = entityContext.getPopulatorFieldValue( method );
            addChild( propertyName, fieldValue, entityContext );
        }
    }

    protected void addChild( String fieldName, Object fieldValue,
            YogaInstanceContext<?> entityContext )
    {
        if ( fieldValue == null )
        {
            return;
        }

        HierarchicalModel model = entityContext.getModel();

        if ( isPrimitive( fieldValue.getClass() ) )
        {
            model.createSimple( fieldName ).addSimple( fieldValue );
        }
        else
        {
            HierarchicalModel childModel;
            if ( Iterable.class.isAssignableFrom( fieldValue.getClass() ) )
            {
                childModel = model.createList( fieldName );
            }
            else
            {
                childModel = model.createChild( fieldName );
            }
            Selector childSelector = entityContext.getFieldSelector().getField( fieldName );
            traverse( fieldValue, childSelector, childModel, entityContext.getRequestContext() );
        }
    }

    // GETTERS / SETTERS

    public void setEnrichers( List<Enricher> enrichers )
    {
        this._enrichers = enrichers;
    }

    public void setInstanceContextFactory( YogaInstanceContextFactory instanceContextFactory )
    {
        this.instanceContextFactory = instanceContextFactory;
    }
}
