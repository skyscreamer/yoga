package org.skyscreamer.yoga.model;

import org.skyscreamer.yoga.view.json.GeneratorAdapter;

import java.io.IOException;

public class ArrayStreamingJsonHierarchicalModel implements ListHierarchicalModel<GeneratorAdapter>
{
    private GeneratorAdapter generator;
    private ObjectStreamingJsonHierarchicalModel objectModel;
    
    public ArrayStreamingJsonHierarchicalModel( GeneratorAdapter generator ) throws IOException
    {
        this.generator = generator;
        objectModel = new ObjectStreamingJsonHierarchicalModel(generator, this);
        start();
    }

    public ArrayStreamingJsonHierarchicalModel(
			GeneratorAdapter generator,
            ObjectStreamingJsonHierarchicalModel objectModel) throws IOException
    {
        this.generator = generator;
        this.objectModel = objectModel;
    }

    public void start() throws IOException
    {
        generator.writeStartArray();
    }

    @Override
    public void finished() throws IOException
    {
        generator.writeEndArray();
    }

    @Override
    public void addValue(Object instance) throws IOException
    {
        generator.writeObject(instance);
    }

    public MapHierarchicalModel<?> createChildMap() throws IOException
    {
        objectModel.start();
        return objectModel;
    }

    @Override
    public GeneratorAdapter getUnderlyingModel()
    {
        return generator;
    }
}
