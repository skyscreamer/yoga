package org.skyscreamer.yoga.populator;

import java.lang.reflect.Method;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class FieldPopulatorRenderingListenerAdapter implements RenderingListener
{

    private FieldPopulatorRegistry _fieldPopulatorRegistry;
    private ResultTraverser _resultTraverser;

    public FieldPopulatorRenderingListenerAdapter()
    {
        this._fieldPopulatorRegistry = new DefaultFieldPopulatorRegistry() ;
    }


    public FieldPopulatorRenderingListenerAdapter( FieldPopulatorRegistry _fieldPopulatorRegistry,
            ResultTraverser _resultTraverser )
    {
        super();
        this._fieldPopulatorRegistry = _fieldPopulatorRegistry;
        this._resultTraverser = _resultTraverser;
    }


    @Override
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD || _fieldPopulatorRegistry == null)
        {
            return;
        }
        Class<?> instanceType = event.getValueType();
        Object populator = _fieldPopulatorRegistry.getFieldPopulator( instanceType );
        if (populator == null)
        {
            return;
        }

        MapHierarchicalModel<?> model = (MapHierarchicalModel<?>) event.getModel();
        Selector selector = event.getSelector();
        YogaRequestContext requestContext = event.getRequestContext();
        Object pojoInstance = event.getValue();

        if (populator != null && selector != null)
        {
            for (Method method : populator.getClass().getDeclaredMethods())
            {
                Class<?>[] parameterTypes = method.getParameterTypes();
                int paramLength = parameterTypes.length;
                if( method.isAnnotationPresent( ExtraField.class ) && paramLength < 2 )
                {
                    String name = method.getAnnotation( ExtraField.class ).value();
    
                    if (selector.containsField( instanceType, name ) && (paramLength == 0 || parameterTypes[0] == instanceType ) )
                    {
                        String propertyName = method.getAnnotation( ExtraField.class ).value();
                        Object fieldValue = getPopulatorFieldValue( method, populator, pojoInstance );
                        _resultTraverser.traverseValue( instanceType, model, selector, requestContext, propertyName, fieldValue );
                    }
                }
            }
        }
    }

    protected Object getPopulatorFieldValue( Method method, Object populator, Object instance )
    {
        switch (method.getParameterTypes().length)
        {
            case 0:
                return retrievePopulatorFieldValue( method, populator );

            case 1:
                return retrievePopulatorFieldValue( method, populator, instance );

            default:
                throw new YogaRuntimeException(
                        String.format(
                                "A Field Populator method can only have 0 or 1 parameters.  Method %s has %d",
                                method.toString(), method.getParameterTypes().length ) );
        }
    }

    protected Object retrievePopulatorFieldValue( Method method, Object populator, Object... args )
    {
        try
        {
            return method.invoke( populator, args );
        }
        catch (Exception e)
        {
            throw new YogaRuntimeException( "Could not invoke " + method, e );
        }
    }

    public void setFieldPopulatorRegistry( FieldPopulatorRegistry _fieldPopulatorRegistry )
    {
        this._fieldPopulatorRegistry = _fieldPopulatorRegistry;
    }

    public void setResultTraverser( ResultTraverser resultTraverser )
    {
        this._resultTraverser = resultTraverser;
    }
    
    public FieldPopulatorRegistry getFieldPopulatorRegistry()
    {
        return _fieldPopulatorRegistry;
    }

}
