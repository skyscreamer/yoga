package org.skyscreamer.yoga.traverser;

import java.lang.reflect.AccessibleObject;

public interface HierarchicalModel 
{
	void addSimple(String field, AccessibleObject getter, Object result);

	HierarchicalModel createChild(String field, AccessibleObject getter, Object result);

	HierarchicalModel createList(String field, AccessibleObject getter, Object result);

}
