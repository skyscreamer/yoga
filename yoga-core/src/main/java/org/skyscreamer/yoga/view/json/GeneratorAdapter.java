package org.skyscreamer.yoga.view.json;

import java.io.IOException;

/**
 * User: nk
 * Date: 12/17/13
 */
public interface GeneratorAdapter {

	void writeStartArray() throws IOException;

	void writeEndArray() throws IOException;

	void writeObject(Object instance) throws IOException;

	void writeStartObject() throws IOException;

	void writeEndObject() throws IOException;

	void writeObjectField(String name, Object result) throws IOException;

	void writeFieldName(String name) throws IOException;

	void close() throws IOException;
}