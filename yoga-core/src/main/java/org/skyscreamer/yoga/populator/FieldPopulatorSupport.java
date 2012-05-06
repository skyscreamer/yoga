package org.skyscreamer.yoga.populator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public abstract class FieldPopulatorSupport implements FieldPopulator
{
    public List<String> getCoreFields()
    {
        return new ArrayList<String>();
    }

    public List<String> getSupportedFields()
    {
        return null;
    }

    public String getUriTemplate()
    {
        return null;
    }
}
