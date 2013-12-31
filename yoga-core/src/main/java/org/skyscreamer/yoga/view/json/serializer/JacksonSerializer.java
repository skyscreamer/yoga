package org.skyscreamer.yoga.view.json.serializer;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nk
 * Date: 12/17/13
 * Time: 6:16 AM
 */
public class JacksonSerializer implements JsonSerialiazer {

    private ObjectMapper objectMapper;

    public JacksonSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override public void serialize(OutputStream out, Object obj) throws IOException {
        objectMapper.writeValue(out, obj);
    }

}
