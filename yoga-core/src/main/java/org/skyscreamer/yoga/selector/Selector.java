package org.skyscreamer.yoga.selector;

import java.lang.reflect.AccessibleObject;


public interface Selector
{
    public Selector getField(String field);

	public boolean containsField(String field, AccessibleObject accessibleObject);
}

