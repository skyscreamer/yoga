package org.skyscreamer.yoga.demo.mapper;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.mapper.ResultTraverser;

public class HibernateResultTraverser extends ResultTraverser
{
   @SuppressWarnings("unchecked")
   @Override
   public Class<?> getClass(Object instance)
   {
      return Hibernate.getClass( instance );
   }
}
