package org.skyscreamer.yoga.view.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: nk
 * Date: 12/17/13
 */
public class Jackson2Serializer implements JsonSerializer {

	private ObjectMapper objectMapper;

	public Jackson2Serializer() {
		this.objectMapper = new ObjectMapper();
	}

	@Override public void serialize(OutputStream out, Object obj) throws IOException {
		objectMapper.writeValue(out, obj);
	}
}