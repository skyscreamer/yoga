package org.skyscreamer.yoga.mapper.enrich;

import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;

public interface Enricher
{
   void enrich(HttpServletResponse response, Object instance, Selector fieldSelector,
         HierarchicalModel model, Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator);
}
