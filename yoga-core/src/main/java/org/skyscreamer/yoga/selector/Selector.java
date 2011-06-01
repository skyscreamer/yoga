package org.skyscreamer.yoga.selector;

import java.beans.PropertyDescriptor;


public interface Selector
{
    public Selector getField(PropertyDescriptor descriptor);

	public boolean containsField(PropertyDescriptor descriptor);
}

