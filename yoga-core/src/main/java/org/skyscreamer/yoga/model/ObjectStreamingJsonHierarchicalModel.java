package org.skyscreamer.yoga.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

public class ObjectStreamingJsonHierarchicalModel implements
        MapHierarchicalModel<JsonGenerator>
{
	

    protected JsonGenerator generator;

    @Override
    public JsonGenerator getUnderlyingModel()
    {
        return generator;
    }


    public ObjectStreamingJsonHierarchicalModel(JsonGenerator generator)
            throws JsonGenerationException, IOException
    {
    	this.generator = generator;
        generator.writeStartObject();
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
        generator.writeStartObject();
        return this;
    }

    public ListHierarchicalModel<JsonGenerator> createChildList(String name) throws IOException
    {
        generator.writeFieldName(name);
        return new ArrayStreamingJsonHierarchicalModel(generator);
    }
}
