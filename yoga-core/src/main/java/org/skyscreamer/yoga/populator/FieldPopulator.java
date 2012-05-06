package org.skyscreamer.yoga.populator;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public interface FieldPopulator
{
    List<String> getCoreFields();

    List<String> getSupportedFields();

    String getUriTemplate();
}
