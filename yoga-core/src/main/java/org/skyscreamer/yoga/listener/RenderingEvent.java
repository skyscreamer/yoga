package org.skyscreamer.yoga.listener;

import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class RenderingEvent<T>
{
    private RenderingEventType type;
    private HierarchicalModel<?> model;
    private T value;
    private Class<T> valueType;
    private YogaRequestContext requestContext;
    private Selector selector;

    public RenderingEvent( RenderingEventType type, HierarchicalModel<?> model, T value,
            Class<T> valueType, YogaRequestContext requestContext, Selector selector )
    {
        super();
        this.type = type;
        this.model = model;
        this.value = value;
        this.valueType = valueType;
        this.requestContext = requestContext;
        this.selector = selector;
    }

    public RenderingEventType getType()
    {
        return type;
    }

    public HierarchicalModel<?> getModel()
    {
        return model;
    }

    public T getValue()
    {
        return value;
    }
    
    public Class<T> getValueType()
    {
        return valueType;
    }
    
    public YogaRequestContext getRequestContext()
    {
        return requestContext;
    }
    
    public Selector getSelector()
    {
        return selector;
    }
}
