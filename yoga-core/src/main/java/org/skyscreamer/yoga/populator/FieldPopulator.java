package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.selector.Selector;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public interface FieldPopulator<M>
{
    Map<String,Object> populateObjectFields( M model, Selector selector );

    List<Map<String,Object>> populateListFields( Collection<M> model, Selector selector );
}
