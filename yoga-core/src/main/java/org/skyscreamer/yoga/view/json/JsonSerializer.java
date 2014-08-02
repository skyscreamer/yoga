package org.skyscreamer.yoga.view.json;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: nk
 * Date: 12/17/13
 */
public interface JsonSerializer {

	void serialize(OutputStream out, Object obj) throws IOException;
}