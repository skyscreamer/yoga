package org.skyscreamer.yoga.selector;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractSelectable implements Selectable
{
    private Set<SelectorField> _selectorFields = Collections.<SelectorField>emptySet();

    public AbstractSelectable() {}

    public AbstractSelectable( Selector selector )
    {
        setSelector( selector );
    }

    public Set<SelectorField> getSelectorFields()
    {
        return _selectorFields;
    }

    public void setSelector( Selector selector )
    {
        _selectorFields = selector != null ? selector.getFields() : Collections.<SelectorField>emptySet();
    }
}
