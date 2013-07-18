package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ArrayStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class StreamingJsonSelectorView extends AbstractYogaView
{
    JsonFactory jsonFactory = new JsonFactory();

    public void setJsonFactory(JsonFactory jsonFactory)
    {
	    this.jsonFactory = jsonFactory;
    }

    @Override
    protected void render(Selector selector, Object value,
            YogaRequestContext context, OutputStream os) throws Exception
    {
        JsonGenerator generator = createGenerator(os);
        HierarchicalModel<JsonGenerator> model = createModel(value, generator);
        _resultTraverser.traverse( value, selector, model, context );
        model.getUnderlyingModel().close();
    }

    protected JsonGenerator createGenerator(OutputStream outputStream)
            throws IOException
    {
		return jsonFactory.createJsonGenerator(outputStream);
    }

    protected HierarchicalModel<JsonGenerator> createModel(Object value,
            JsonGenerator generator) throws IOException,
            JsonGenerationException
    {
        if (value instanceof Iterable)
        {
            return new ArrayStreamingJsonHierarchicalModel(generator);
        } 
        else
        {
            return new ObjectStreamingJsonHierarchicalModel(generator);
        }
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
