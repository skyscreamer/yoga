package org.skyscreamer.yoga.view.json.serializer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nk
 * Date: 12/17/13
 * Time: 6:17 AM
 */
public interface JsonSerialiazer {

    public void serialize(OutputStream out, Object obj) throws IOException;
}
