package org.skyscreamer.yoga.springmvc.mapper;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.mapper.ResultTraverser;

public class HibernateResultTraverser extends ResultTraverser
{
   @SuppressWarnings("unchecked")
   @Override
   public Class<? extends Object> getClass(Object instance)
   {
      return Hibernate.getClass(instance);
   }

}
