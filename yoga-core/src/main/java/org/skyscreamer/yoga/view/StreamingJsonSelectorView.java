package org.skyscreamer.yoga.view;

import java.io.IOException;
import java.io.OutputStream;

import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ArrayStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectStreamingJsonHierarchicalModel;
import org.skyscreamer.yoga.util.ClassUtil;
import org.skyscreamer.yoga.view.json.GeneratorAdapter;
import org.skyscreamer.yoga.view.json.Jackson2JsonGeneratorAdapter;
import org.skyscreamer.yoga.view.json.JacksonJsonGeneratorAdapter;

public class StreamingJsonSelectorView extends AbstractYogaView
{
    @Override
    protected void render(Object value, YogaRequestContext context, OutputStream os) throws Exception
    {
		GeneratorAdapter generator = getGeneratorAdapter(os);
        HierarchicalModel<GeneratorAdapter> model = createModel( value, generator );
        _resultTraverser.traverse( value, context.getSelector(), model, context );
        model.getUnderlyingModel().close();
    }

	private GeneratorAdapter getGeneratorAdapter( OutputStream os ) throws IOException
	{
		if ( ClassUtil.jacksonPresent )
		{
			return new JacksonJsonGeneratorAdapter( os );
		}
		else if ( ClassUtil.jackson2Present )
		{
			return new Jackson2JsonGeneratorAdapter( os );
		}
		else throw new IllegalStateException( "Jackson Library not in classpath" );
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
