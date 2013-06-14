package org.skyscreamer.yoga.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;

public class ArrayStreamingJsonHierarchicalModel implements ListHierarchicalModel<JsonGenerator>
{

    private JsonGenerator generator;

	public ArrayStreamingJsonHierarchicalModel(JsonGenerator generator) throws IOException
    {
        this.generator = generator;
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
	    return new ObjectStreamingJsonHierarchicalModel(generator);
    }

	@Override
    public JsonGenerator getUnderlyingModel()
    {
	    return generator;
    }

}
