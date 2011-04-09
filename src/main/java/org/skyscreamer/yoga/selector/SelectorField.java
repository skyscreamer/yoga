package org.skyscreamer.yoga.selector;

public class SelectorField
{
    private String _fieldName;
    private Selector _selector;

    public SelectorField( String fieldName, Selector selector )
    {
        _fieldName = fieldName;
        _selector = selector;
    }

    public String getFieldName()
    {
        return _fieldName;
    }

    public Selector getSelector()
    {
        return _selector;
    }
}
