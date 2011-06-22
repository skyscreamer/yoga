package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;
import java.util.Set;

public interface Selector
{
    Selector getField( PropertyDescriptor descriptor );

	boolean containsField( PropertyDescriptor descriptor );

    Selector getField( String fieldName );

    Set<String> getFieldNames();
}

