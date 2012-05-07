package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class JsonSelectorView extends AbstractYogaView
{
    @Override
    public void render(OutputStream outputStream, Selector selector, Object value,
            HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Object viewData;
        YogaRequestContext context = new YogaRequestContext( getHrefSuffix(), request, response );
        if (value instanceof Iterable<?>)
        {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (Object instance : (Iterable<?>) value)
            {
                list.add( getSingleResult( instance, selector, context ) );
            }
            viewData = list;
        }
        else
        {
            viewData = getSingleResult( value, selector, context );
        }
        getObjectMapper().writeValue( outputStream, viewData );
    }

    protected ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }

    protected Map<String, Object> getSingleResult(Object value, Selector selector,
            YogaRequestContext context)
    {
        MapHierarchicalModel model = new MapHierarchicalModel();
        resultTraverser.traverse( value, selector, model, context );
        return model.getObjectTree();
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
