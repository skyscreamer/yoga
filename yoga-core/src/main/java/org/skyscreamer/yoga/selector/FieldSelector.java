package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FieldSelector implements Selector
{
    private Map<String, Selector> _fields = new HashMap<String, Selector>();

    public Map<String, Selector> getFields()
    {
        return _fields;
    }

    @Override
    public Selector getField( String field )
    {
        return _fields.get( field );
    }

    @Override
    public boolean containsField( PropertyDescriptor property, FieldPopulator fieldPopulator )
    {
        String propertyName = property.getName();
        return containsField( propertyName )
                || (fieldPopulator != null && fieldPopulator.getSupportedFields() != null && fieldPopulator
                .getSupportedFields().contains( propertyName ));
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

    public void addField( String key, Selector selector )
    {
        _fields.put( key, selector );
    }
}
