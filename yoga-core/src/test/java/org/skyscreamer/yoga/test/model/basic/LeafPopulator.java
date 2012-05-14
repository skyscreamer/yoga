package org.skyscreamer.yoga.test.model.basic;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.annotations.PopulationExtension;

@PopulationExtension( BasicTestDataLeaf.class )
public class LeafPopulator
{
    @ExtraField("someValue")
    public String getSomeValue()
    {
        return "someValue";
    }
}
