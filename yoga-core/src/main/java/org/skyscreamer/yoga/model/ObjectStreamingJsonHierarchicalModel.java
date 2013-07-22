package org.skyscreamer.yoga.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;

public class ObjectStreamingJsonHierarchicalModel implements
        MapHierarchicalModel<JsonGenerator>
{
    

    protected JsonGenerator generator;
	private ArrayStreamingJsonHierarchicalModel arrayModel;

    public ObjectStreamingJsonHierarchicalModel(JsonGenerator generator)
            throws IOException
    {
        this.generator = generator;
        this.arrayModel = new ArrayStreamingJsonHierarchicalModel(generator, this);
        this.start();
    }

    public ObjectStreamingJsonHierarchicalModel(
            JsonGenerator generator,
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
    public JsonGenerator getUnderlyingModel()
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

    public MapHierarchicalModel<JsonGenerator> createChildMap(String name) throws IOException
    {
        generator.writeFieldName(name);
        this.start();
        return this;
    }

    public ListHierarchicalModel<JsonGenerator> createChildList(String name) throws IOException
    {
        generator.writeFieldName(name);
        arrayModel.start();
        return arrayModel;
    }
}
