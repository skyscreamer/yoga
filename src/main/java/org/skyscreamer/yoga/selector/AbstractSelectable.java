package org.skyscreamer.yoga.selector;

import java.util.Set;

public abstract class AbstractSelectable implements Selectable
{
    private Set<SelectorField> _selectorFields;

    public AbstractSelectable( Selector selector )
    {
        _selectorFields = selector.getFields();
    }

    public Set<SelectorField> getSelectorFields()
    {
        return _selectorFields;
    }
}

