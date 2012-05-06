package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FieldSelector implements Selector
{
    Map<String, Selector> _fields = new HashMap<String, Selector>();

    public Map<String, Selector> getFields()
    {
        return _fields;
    }

<<<<<<< HEAD:yoga-core/src/main/java/org/skyscreamer/yoga/selector/DefinedSelectorImpl.java
    public DefinedSelectorImpl getField(String field)
=======
    @Override
    public Selector getField( PropertyDescriptor property )
    {
        return getField( property.getName() );
    }

    @Override
    public Selector getField( String field )
>>>>>>> upstream/master:yoga-core/src/main/java/org/skyscreamer/yoga/selector/FieldSelector.java
    {
        return _fields.get( field );
    }

    @Override
    public boolean containsField(PropertyDescriptor property, FieldPopulator fieldPopulator)
    {
        return (fieldPopulator != null && fieldPopulator.getSupportedFields() != null && fieldPopulator
                .getSupportedFields().contains( property.getName() ))
                || containsField( property.getName() );
    }

    public boolean containsField(String field)
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
