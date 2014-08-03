package org.skyscreamer.yoga.view.json;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: nk
 * Date: 12/17/13
 */
public class JacksonSerializer implements JsonSerializer {

	private ObjectMapper objectMapper;

	public JacksonSerializer() {
		this.objectMapper = new ObjectMapper();
	}

	@Override public void serialize(OutputStream out, Object obj) throws IOException {
		objectMapper.writeValue(out, obj);
	}

}