package org.skyscreamer.yoga.listener;

import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class RenderingEvent
{
    private RenderingEventType type;
    private HierarchicalModel<?> model;
    private String key;
    private Object value;
    private Class<?> valueType;
    private YogaRequestContext requestContext;
    private Selector selector;

    public RenderingEvent( RenderingEventType type, HierarchicalModel<?> model, Object value,
            Class<?> valueType, YogaRequestContext requestContext, Selector selector )
    {
        super();
        this.type = type;
        this.model = model;
        this.value = value;
        this.valueType = valueType;
        this.requestContext = requestContext;
        this.selector = selector;
    }

    public RenderingEvent( RenderingEventType type, HierarchicalModel<?> model, String key,
            Object value, Class<?> valueType, YogaRequestContext requestContext, Selector selector )
    {
        super();
        this.type = type;
        this.model = model;
        this.key = key;
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

    public String getKey()
    {
        return key;
    }

    public Object getValue()
    {
        return value;
    }
    
    public Class<?> getValueType()
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
