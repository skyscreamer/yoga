package org.skyscreamer.yoga.populator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public interface FieldPopulator<M>
{
    List<String> getCoreFields();

    List<String> getSupportedFields();

    String getUriTemplate();
}
