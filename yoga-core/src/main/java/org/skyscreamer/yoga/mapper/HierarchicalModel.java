package org.skyscreamer.yoga.mapper;

import java.beans.PropertyDescriptor;

public interface HierarchicalModel 
{
	void addSimple(PropertyDescriptor property, Object value);

	void addSimple(String name, Object result);

	HierarchicalModel createChild(PropertyDescriptor property, Object value);

   HierarchicalModel createChild(String property, Object value);
	
	HierarchicalModel createList(PropertyDescriptor property, Object value);

   HierarchicalModel createList(String property, Object value);
}
