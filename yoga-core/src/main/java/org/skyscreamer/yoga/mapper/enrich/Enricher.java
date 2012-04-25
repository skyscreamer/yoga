package org.skyscreamer.yoga.mapper.enrich;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverserContext;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;

public interface Enricher
{
    void enrich( Object instance, Selector fieldSelector, HierarchicalModel model,
            Class<?> instanceType, FieldPopulator<?> populator, ResultTraverserContext context );
}
