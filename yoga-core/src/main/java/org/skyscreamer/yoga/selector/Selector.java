package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;


public interface Selector
{
    Selector getField( PropertyDescriptor descriptor );

	boolean containsField( PropertyDescriptor descriptor );
}

