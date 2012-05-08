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

    public void traverse( Object instance, Selector fieldSelector, HierarchicalModel<?> model,
            YogaRequestContext context )
    {
        if ( instance == null )
        {
            return;
        }

        if (Iterable.class.isAssignableFrom( instance.getClass() ))
        {
            for (Object o : (Iterable<?>) instance)

            {
                if ( isPrimitive( o.getClass() ) )
                {
                    model.addSimple( o );
                }
                else
                {
                    HierarchicalModel<?> childModel = model.createChild();
                    traverse( o, fieldSelector, childModel, context );
                }
            }
        }
        else
        {
            YogaInstanceContext<?> entityContext = instanceContextFactory
                .createEntityContext( instance, fieldSelector, model, context );
            
            for (Enricher enricher : _enrichers)
            {
                enricher.enrich( entityContext );
            }

            addInstanceFields( entityContext );
            addPopulatorExtraFields( entityContext );
        }
    }

    protected void addInstanceFields(YogaInstanceContext<?> entityContext)
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

    protected void addChild(String fieldName, Object fieldValue,
            YogaInstanceContext<?> parentContext)
    {
        if ( fieldValue == null )
        {
            return;
        }

        HierarchicalModel<?> model = parentContext.getModel();

        if ( isPrimitive( fieldValue.getClass() ) )
        {
            model.addSimple( fieldName, fieldValue );
        }
        else
        {
            HierarchicalModel<?> childModel = getChildModel( fieldName, fieldValue, model );
            Selector childSelector = parentContext.getFieldSelector().getField( fieldName );
            traverse( fieldValue, childSelector, childModel, parentContext.getRequestContext() );
        }
    }


    protected HierarchicalModel<?> getChildModel(String fieldName, Object fieldValue,
            HierarchicalModel<?> model)
    {
        if (Iterable.class.isAssignableFrom( fieldValue.getClass() ))
        {
            return model.createList( fieldName );
        }
        else
        {
            return model.createChild( fieldName );
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
