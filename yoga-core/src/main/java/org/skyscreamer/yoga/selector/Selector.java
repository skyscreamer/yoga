package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

public interface Selector
{
    Selector getField(String fieldName);

    boolean containsField(PropertyDescriptor descriptor, FieldPopulator fieldPopulator);

    boolean containsField(String property);

<<<<<<< HEAD
    Set<String> getFieldNames();
=======
   Set<String> getFieldNames();

    Map<String, Selector> getFields();
>>>>>>> upstream/master
}
