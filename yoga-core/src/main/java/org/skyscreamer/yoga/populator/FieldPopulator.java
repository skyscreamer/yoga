package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public interface FieldPopulator<M>
{
   void addExtraFields(Selector selector, M model, ResultTraverser traverser, HierarchicalModel output);
}
