package org.skyscreamer.yoga.test.model.basic;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;

public class LeafConfiguration extends YogaEntityConfiguration<BasicTestDataLeaf>
{
    @ExtraField("someField")
    public String getSomeField()
    {
        return "customValue";
    }
}
