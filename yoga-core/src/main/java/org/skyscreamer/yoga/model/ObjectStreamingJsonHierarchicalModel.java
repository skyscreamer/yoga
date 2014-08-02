package org.skyscreamer.yoga.model;

import org.skyscreamer.yoga.view.json.GeneratorAdapter;

import java.io.IOException;

public class ObjectStreamingJsonHierarchicalModel implements
        MapHierarchicalModel<GeneratorAdapter>
{
    protected GeneratorAdapter generator;
	private ArrayStreamingJsonHierarchicalModel arrayModel;

    public ObjectStreamingJsonHierarchicalModel(GeneratorAdapter generator)
            throws IOException
    {
        this.generator = generator;
        this.arrayModel = new ArrayStreamingJsonHierarchicalModel(generator, this);
        this.start();
    }

    public ObjectStreamingJsonHierarchicalModel(
			GeneratorAdapter generator,
            ArrayStreamingJsonHierarchicalModel arrayModel) throws IOException
    {
        this.generator = generator;
        this.arrayModel = arrayModel;
    }
    
    public void start() throws IOException
    {
        generator.writeStartObject();
    }

	@Override
    public GeneratorAdapter getUnderlyingModel()
    {
        return generator;
    }

    @Override
    public void finished() throws IOException
    {
        generator.writeEndObject();
    }

    @Override
    public void addProperty(String name, Object result) throws IOException
    {
        generator.writeObjectField(name, result);
    }

    public MapHierarchicalModel<GeneratorAdapter> createChildMap(String name) throws IOException
    {
        generator.writeFieldName(name);
        this.start();
        return this;
    }

    public ListHierarchicalModel<GeneratorAdapter> createChildList(String name) throws IOException
    {
        generator.writeFieldName(name);
        arrayModel.start();
        return arrayModel;
    }
}
