package org.skyscreamer.yoga.springmvc.view;

import java.io.Writer;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ListHierarchicalModel;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class JsonSelectorView extends AbstractYogaView
{
    @Override
    public void render(Selector selector, Object value, YogaRequestContext requestContext) throws Exception
    {
        HierarchicalModel<?> model = getModel( value );
        resultTraverser.traverse( value, selector, model, requestContext );
        Writer writer = requestContext.getResponse().getWriter();
        getObjectMapper().writeValue( writer, model.getUnderlyingModel() );
    }

    protected HierarchicalModel<?> getModel(Object value)
    {
        if (value instanceof Iterable<?>)
        {
            return new ListHierarchicalModel();
        }
        else
        {
            return new MapHierarchicalModel();
        }

    }

    protected ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }

    @Override
    public String getContentType()
    {
        return "application/json";
    }

    @Override
    public String getHrefSuffix()
    {
        return "json";
    }
}
