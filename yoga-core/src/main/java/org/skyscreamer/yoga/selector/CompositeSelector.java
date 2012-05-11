package org.skyscreamer.yoga.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.util.CollectionUtils;

public class CompositeSelector implements Selector
{
    private List<Selector> _selectors = new ArrayList<Selector>();

    private CompositeSelector()
    {
    }

    public CompositeSelector( Selector... selectors )
    {
        this( Arrays.asList( selectors ) );
    }

    public CompositeSelector( Iterable<Selector> selectors )
    {
        for (Selector selector : selectors)
        {
            add( selector );
        }
    }

    public void add( Selector selector )
    {
        if (selector != null)
        {
            this._selectors.add( selector );
        }
    }

    @Override
    public String toString()
    {
        return getClass().getName() + ": " + _selectors;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        CompositeSelector child = new CompositeSelector();
        for (Selector selector : _selectors)
        {
            child.add( selector.getChildSelector( instanceType, fieldName ) );
        }
        return child;
    }

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        for (Selector selector : _selectors)
        {
            if (selector.containsField( instanceType, property ))
                return true;
        }
        return false;
    }

    @Override
    public Set<String> getSelectedFieldNames( Class<?> instanceType )
    {
        Set<String> fieldNames = new TreeSet<String>();
        for (Selector selector : _selectors)
        {
            Set<String> subSelectorFieldNames = selector.getSelectedFieldNames( instanceType );
            if (!CollectionUtils.isEmpty( subSelectorFieldNames ))
            {
                fieldNames.addAll( subSelectorFieldNames );
            }
        }
        return fieldNames;
    }

    @Override
    public Map<String, Selector> getSelectors( Class<?> instanceType )
    {
        Map<String, Selector> fields = new TreeMap<String, Selector>();
        for (Selector selector : _selectors)
        {
            Map<String, Selector> subSelectorFields = selector.getSelectors( instanceType );
            if (!CollectionUtils.isEmpty( subSelectorFields ))
            {
                fields.putAll( subSelectorFields );
            }
        }
        return fields;
    }

    @Override
    public Set<String> getAllPossibleFields( Class<?> instanceType )
    {
        TreeSet<String> allFields = new TreeSet<String>();
        for (Selector selector : _selectors)
        {
            allFields.addAll( selector.getAllPossibleFields( instanceType ) );
        }
        return allFields;
    }

    public int subSelectorCount()
    {
        return _selectors.size();
    }

    @Override
    public boolean isInfluencedExternally()
    {
        for (Selector selector : _selectors)
        {
            if (selector.isInfluencedExternally())
            {
                return true;
            }
        }
        return false;
    }
}
