package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ArrayStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.util.JacksonLibraryUtil;
import org.skyscreamer.yoga.view.json.GeneratorAdapter;

public class StreamingJsonSelectorView extends AbstractYogaView
{
    @Override
    protected void render(Object value, YogaRequestContext context, OutputStream os) throws Exception
    {
		GeneratorAdapter generator = JacksonLibraryUtil.selectGeneratorAdapter(os);
        HierarchicalModel<GeneratorAdapter> model = createModel( value, generator );
        _resultTraverser.traverse( value, context.getSelector(), model, context );
        model.getUnderlyingModel().close();
    }

	protected HierarchicalModel<GeneratorAdapter> createModel( Object value, GeneratorAdapter generator )
			throws IOException
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
