package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.selector.Selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 * Date: 4/21/11
 * Time: 2:52 PM
 */
public abstract class AbstractFieldPopulator<T, D> implements GenericFieldPopulator<T>
{
    public Map<String, Object> populate( T instance, Selector fieldSelector )
    {
        D dto = convertToDto( instance, fieldSelector );
        Map<String,Object> result = convertToMap( dto );
        return result;
    }

    public List<Map<String,Object>> populate( Collection<? extends T> instances, Selector fieldSelector )
    {
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        for ( T instance : instances )
        {
            result.add( populate( instance, fieldSelector ) );
        }
        return result;
    }

    protected abstract D convertToDto( T instance, Selector fieldSelector );

    protected abstract Map<String,Object> convertToMap( D dto );
}
