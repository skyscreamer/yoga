package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectListHierarchicalModelImpl;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.selector.Selector;

public class JsonSelectorView extends AbstractYogaView
{
    @Override
    public void render1( Selector selector, Object value, YogaRequestContext requestContext,
            OutputStream outputStream ) throws IOException
    {
        HierarchicalModel<?> model = getModel( value );
        _resultTraverser.traverse( value, selector, model, requestContext );
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
