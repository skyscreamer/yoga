package org.skyscreamer.yoga.view;

import javax.servlet.ServletOutputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectListHierarchicalModelImpl;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.selector.Selector;

public class JsonSelectorView extends AbstractYogaView
{
    @Override
    public void render(Selector selector, Object value, YogaRequestContext requestContext) throws Exception
    {
        HierarchicalModel<?> model = getModel( value );
        resultTraverser.traverse( value, selector, model, requestContext );
        ServletOutputStream outputStream = requestContext.getResponse().getOutputStream();
        getObjectMapper().writeValue( outputStream, model.getUnderlyingModel() );
    }

    protected HierarchicalModel<?> getModel(Object value)
    {
        if (value instanceof Iterable<?>)
        {
            return new ObjectListHierarchicalModelImpl();
        }
        else
        {
            return new ObjectMapHierarchicalModelImpl();
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
