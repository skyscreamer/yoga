package org.skyscreamer.yoga.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;

public class ArrayStreamingJsonHierarchicalModel implements ListHierarchicalModel<JsonGenerator>
{

    private JsonGenerator generator;
    private ObjectStreamingJsonHierarchicalModel objectModel;
    
    public ArrayStreamingJsonHierarchicalModel(JsonGenerator generator) throws IOException
    {
        this.generator = generator;
        objectModel = new ObjectStreamingJsonHierarchicalModel(generator, this);
        start();
    }

    public ArrayStreamingJsonHierarchicalModel(
            JsonGenerator generator,
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
    public JsonGenerator getUnderlyingModel()
    {
        return generator;
    }

}
