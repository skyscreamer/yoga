package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ArrayStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.selector.Selector;

public class StreamingJsonYogaView extends AbstractYogaView
{
    @Override
    protected void render(Selector selector, Object value,
            YogaRequestContext context, OutputStream os) throws Exception
    {
        ServletOutputStream outputStream = context.getResponse().getOutputStream();
        HierarchicalModel<JsonGenerator> model = createModel(outputStream, value);
        _resultTraverser.traverse( value, selector, model, context );
        model.getUnderlyingModel().close();
        outputStream.flush();
        outputStream.close();
    }

    protected HierarchicalModel<JsonGenerator> createModel(ServletOutputStream outputStream , Object value) 
            throws IOException
    {
        if (value instanceof Iterable)
        {
            return new ArrayStreamingJsonHierarchicalModel(createGenerator(outputStream));
        } 
        else
        {
            return new ObjectStreamingJsonHierarchicalModel(createGenerator(outputStream));
        }
    }

    protected JsonGenerator createGenerator(OutputStream outputStream) throws IOException
    {
		return createGenerator(outputStream);
    }

	protected JsonGenerator createGenerator(ServletOutputStream outputStream)
            throws IOException
    {
	    return new JsonFactory().createJsonGenerator(outputStream);
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
