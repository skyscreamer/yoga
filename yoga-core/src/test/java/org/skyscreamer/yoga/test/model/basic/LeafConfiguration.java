package org.skyscreamer.yoga.test.model.basic;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;

public class LeafConfiguration extends YogaEntityConfiguration
{
    @ExtraField("someValue")
    public String getSomeValue()
    {
        return "someValue";
    }

    @Override
    public Class getEntityClass() {
        return BasicTestDataLeaf.class;
    }
}
