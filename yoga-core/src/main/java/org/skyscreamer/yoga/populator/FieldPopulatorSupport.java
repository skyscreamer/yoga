package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.selector.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public abstract class FieldPopulatorSupport<M> implements FieldPopulator<M>
{
    public void addExtraFields( Selector selector, M model, ResultTraverser traverser, HierarchicalModel output )
    {
    }

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
