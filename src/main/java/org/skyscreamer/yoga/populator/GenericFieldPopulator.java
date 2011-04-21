package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.selector.Selector;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 * Date: 4/21/11
 * Time: 2:03 PM
 */
public interface GenericFieldPopulator<T>
{
    Map<String,Object> populate( T instance, Selector FieldSelector );

    List<Map<String,Object>> populate( Collection<T> instances, Selector fieldSelector );
}
