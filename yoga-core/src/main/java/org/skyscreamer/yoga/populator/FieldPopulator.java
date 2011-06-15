package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.selector.Selector;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public interface FieldPopulator<M,D extends Object>
{
    D populateObjectFields( M model, Selector selector );

    List<D> populateListFields( List<M> model, Selector selector );
}
