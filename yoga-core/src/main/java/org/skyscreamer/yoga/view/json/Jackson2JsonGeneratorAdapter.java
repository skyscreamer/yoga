package org.skyscreamer.yoga.view.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: nk
 * Date: 12/17/13
 */
public class Jackson2JsonGeneratorAdapter implements GeneratorAdapter {

	private static final JsonFactory jsonFactory = new JsonFactory();

	private JsonGenerator generator;

	public Jackson2JsonGeneratorAdapter(OutputStream out) throws IOException {
		this.generator = this.createGenerator(out);
	}

	protected JsonGenerator createGenerator(OutputStream outputStream)
			throws IOException {
		return jsonFactory.createGenerator(outputStream);
	}

	@Override
	public void writeFieldName(String name) throws IOException {
		generator.writeFieldName(name);
	}

	@Override
	public void close() throws IOException {
		generator.close();
	}

	@Override
	public void writeObjectField(String name, Object result) throws IOException {
		generator.writeObjectField(name, result);
	}

	@Override
	public void writeEndObject() throws IOException {
		generator.writeEndObject();
	}

	@Override
	public void writeStartObject() throws IOException {
		generator.writeStartObject();
	}

	@Override
	public void writeStartArray() throws IOException {
		generator.writeStartArray();
	}

	@Override
	public void writeEndArray() throws IOException {
		generator.writeEndArray();
	}

	@Override
	public void writeObject(Object instance) throws IOException {
		generator.writeObject(instance);
	}
}
