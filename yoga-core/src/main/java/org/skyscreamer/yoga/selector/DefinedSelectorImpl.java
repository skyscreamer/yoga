package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefinedSelectorImpl implements Selector
{
    Map<String, DefinedSelectorImpl> _fields = new HashMap<String, DefinedSelectorImpl>();

    public Map<String, DefinedSelectorImpl> getFields()
    {
        return _fields;
    }

    @Override
    public DefinedSelectorImpl getField( PropertyDescriptor property )
    {
        return getField( property.getName() );
    }

    public DefinedSelectorImpl getField( String field )
    {
        return _fields.get( field );
    }

    @Override
    public boolean containsField( PropertyDescriptor property, FieldPopulator<?> fieldPopulator )
    {
        return (fieldPopulator != null && fieldPopulator.getSupportedFields() != null
                && fieldPopulator.getSupportedFields().contains( property.getName() ))
                || containsField( property.getName() );
    }

    public boolean containsField( String field )
    {
        return _fields.containsKey( field );
    }

    public String toString()
    {
        return _fields.toString();
    }

    public Set<String> getFieldNames()
    {
        return _fields.keySet();
    }
}
