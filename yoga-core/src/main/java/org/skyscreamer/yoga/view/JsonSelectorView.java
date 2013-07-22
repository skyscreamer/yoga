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
    private ObjectMapper objectMapper;

    public JsonSelectorView()
    {
        this.objectMapper = createObjectMapper();
    }

    @Override
    public void render( Selector selector, Object value, YogaRequestContext requestContext,
            OutputStream outputStream ) throws IOException
    {
        HierarchicalModel<?> model = null;
        if (value instanceof Iterable<?>)
        {
            ObjectListHierarchicalModelImpl listModel = new ObjectListHierarchicalModelImpl();
            _resultTraverser.traverseIterable((Iterable<?>) value, selector, listModel, requestContext);
            model = listModel;
        }
        else
        {
            ObjectMapHierarchicalModelImpl mapModel = new ObjectMapHierarchicalModelImpl();
            _resultTraverser.traversePojo(value, selector, mapModel, requestContext);
            model = mapModel;
        }
        objectMapper.writeValue( outputStream, model.getUnderlyingModel() );
    }

    protected ObjectMapper createObjectMapper()
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
