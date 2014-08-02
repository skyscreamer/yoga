package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectListHierarchicalModelImpl;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.util.ClassUtil;
import org.skyscreamer.yoga.view.json.Jackson2Serializer;
import org.skyscreamer.yoga.view.json.JacksonSerializer;
import org.skyscreamer.yoga.view.json.JsonSerializer;

public class JsonSelectorView extends AbstractYogaView
{
    private JsonSerializer jsonSerializer;

    public JsonSelectorView()
    {
        if ( ClassUtil.jacksonPresent )
		{
			jsonSerializer = new JacksonSerializer();
		}
		else if ( ClassUtil.jackson2Present )
		{
			jsonSerializer = new Jackson2Serializer();
		}
		else throw new IllegalStateException( "Jackson library not in classpath" );
    }

	public JsonSelectorView(JsonSerializer jsonSerializer)
	{
		this.jsonSerializer = jsonSerializer;
	}

	@Override
    public void render( Object value, YogaRequestContext requestContext, OutputStream outputStream ) throws IOException
    {
        HierarchicalModel<?> model;
        if (value instanceof Iterable<?>)
        {
            ObjectListHierarchicalModelImpl listModel = new ObjectListHierarchicalModelImpl();
            _resultTraverser.traverseIterable( (Iterable<?>) value, requestContext.getSelector(), listModel, requestContext );
            model = listModel;
        }
        else
        {
            ObjectMapHierarchicalModelImpl mapModel = new ObjectMapHierarchicalModelImpl();
            _resultTraverser.traversePojo( value, requestContext.getSelector(), mapModel, requestContext );
            model = mapModel;
        }
		jsonSerializer.serialize(outputStream, model.getUnderlyingModel());
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

	public void setSerializer(JsonSerializer jsonSerializer)
	{
		this.jsonSerializer = jsonSerializer;
	}
}
