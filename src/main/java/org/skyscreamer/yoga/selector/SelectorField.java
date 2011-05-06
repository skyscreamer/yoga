package org.skyscreamer.yoga.selector;

public class SelectorField
{
    private String _fieldName;
    private DefinedSelectorImpl _selector;

    public SelectorField( String fieldName, DefinedSelectorImpl selector )
    {
        _fieldName = fieldName;
        _selector = selector;
    }

    public String getFieldName()
    {
        return _fieldName;
    }

    public DefinedSelectorImpl getSelector()
    {
        return _selector;
    }
}
