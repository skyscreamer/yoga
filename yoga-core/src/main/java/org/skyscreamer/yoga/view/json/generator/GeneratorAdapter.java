package org.skyscreamer.yoga.view.json.generator;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nk
 * Date: 12/17/13
 * Time: 7:15 AM
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
