package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.*;

public class CombinedSelector implements Selector
{
    Collection<Selector> _selectors = new ArrayList<Selector>();

    public CombinedSelector(Selector... selectors)
    {
        this( Arrays.asList( selectors ) );
    }

    public CombinedSelector(Iterable<Selector> selectors)
    {
        for (Selector selector : selectors)
        {
            if (selector != null)
            {
                this._selectors.add( selector );
            }
        }
    }

    @Override
    public Selector getField(PropertyDescriptor propertyDescriptor)
    {
        List<Selector> children = new ArrayList<Selector>();
        for (Selector s : _selectors)
        {
            children.add( s.getField( propertyDescriptor ) );
        }
        return new CombinedSelector( children );
    }

    public Selector getField(String fieldName)
    {
        List<Selector> children = new ArrayList<Selector>();
        for (Selector s : _selectors)
        {
            children.add( s.getField( fieldName ) );
        }
        return new CombinedSelector( children );
    }

    @Override
    public boolean containsField(PropertyDescriptor property, FieldPopulator<?> fieldPopulator)
    {
        for (Selector selector : _selectors)
        {
            if (selector.containsField( property, fieldPopulator ))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsField(String property)
    {
        for (Selector selector : _selectors)
        {
            if (selector.containsField( property ))
            {
                return true;
            }
        }
        return false;
    }

    public Set<String> getFieldNames()
    {
        Set<String> result = new HashSet<String>();
        for (Selector selector : _selectors)
        {
            result.addAll( selector.getFieldNames() );
        }
        return result;
    }
}
