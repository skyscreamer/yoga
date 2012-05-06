package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

import org.skyscreamer.yoga.populator.FieldPopulator;

public interface Selector
{
    Selector getField(String fieldName);

    boolean containsField(PropertyDescriptor descriptor, FieldPopulator fieldPopulator);

    boolean containsField(String property);

    Set<String> getFieldNames();

    Map<String, Selector> getFields();
}
