package org.skyscreamer.yoga.mapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class YogaInstanceContext<T> implements Cloneable
{
    private YogaRequestContext requestContext;

    private Selector fieldSelector;
    private HierarchicalModel<?> model;
    private FieldPopulator populator;
    private T instance;
    private Class<T> instanceType;

    public YogaInstanceContext()
    {

    }

    public YogaInstanceContext( T instance, Class<T> instanceType, Selector fieldSelector,
            HierarchicalModel<?> model, YogaRequestContext requestContext )
    {
        this.instance = instance;
        this.instanceType = instanceType;
        this.fieldSelector = fieldSelector;
        this.model = model;
        this.requestContext = requestContext;
    }

    public YogaRequestContext getRequestContext()
    {
        return requestContext;
    }

    public void setRequestContext( YogaRequestContext requestContext )
    {
        this.requestContext = requestContext;
    }

    public Selector getFieldSelector()
    {
        return fieldSelector;
    }

    public void setFieldSelector( Selector fieldSelector )
    {
        this.fieldSelector = fieldSelector;
    }

    public HierarchicalModel<?> getModel()
    {
        return model;
    }

    public void setModel( HierarchicalModel<?> model )
    {
        this.model = model;
    }

    public FieldPopulator getPopulator()
    {
        return populator;
    }

    public void setPopulator( FieldPopulator populator )
    {
        this.populator = populator;
    }

    public T getInstance()
    {
        return instance;
    }

    public void setInstance( T instance )
    {
        this.instance = instance;
    }

    public Class<T> getInstanceType()
    {
        return instanceType;
    }

    public void setInstanceType( Class<T> instanceType )
    {
        this.instanceType = instanceType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public YogaInstanceContext<T> clone()
    {
        try
        {
            return (YogaInstanceContext<T>) super.clone();
        }
        catch ( CloneNotSupportedException e )
        {
            throw new IllegalStateException( e );
        }
    }

    public boolean containsInstanceField( PropertyDescriptor property )
    {
        return getFieldSelector().containsField( property, getPopulator() );
    }

    public Object getInstanceFieldValue( String propertyName )
    {
        try
        {
            return PropertyUtils.getNestedProperty( instance, propertyName );
        }
        catch ( Exception e )
        {
            throw new YogaRuntimeException( e );
        }
    }

    public List<Method> getPopulatorExtraFieldMethods()
    {
        List<Method> result = new ArrayList<Method>();
        if ( populator != null && fieldSelector != null )
        {
            for ( Method method : populator.getClass().getDeclaredMethods() )
            {
                if ( isSelectedPopulatorMethod( method ) )
                {
                    result.add( method );
                }
            }
        }
        return result;
    }

    public boolean isSelectedPopulatorMethod( Method method )
    {
        return method.isAnnotationPresent( ExtraField.class )
                && isPopulatorField( instanceType, method.getParameterTypes() )
                && this.fieldSelector.containsField( method.getAnnotation( ExtraField.class )
                .value() );
    }

    public static boolean isPopulatorField( Class<?> instanceType, Class<?>[] parameterTypes )
    {
        return parameterTypes.length == 0
                || (parameterTypes.length == 1 && parameterTypes[0].equals( instanceType ));
    }

    public Object getPopulatorFieldValue( Method method )
    {
        switch ( method.getParameterTypes().length )
        {
            case 0:
                return retrievePopulatorFieldValue( method );

            case 1:
                return retrievePopulatorFieldValue( method, instance );

            default:
                throw new YogaRuntimeException(
                        "A Field Populator method can only have 0 or 1 parameters.  Method "
                                + method + " has more" );
        }
    }

    protected Object retrievePopulatorFieldValue( Method method, Object... args )
    {
        try
        {
            return method.invoke( populator, args );
        }
        catch ( Exception e )
        {
            throw new YogaRuntimeException( "Could not invoke " + method, e );
        }
    }
}
