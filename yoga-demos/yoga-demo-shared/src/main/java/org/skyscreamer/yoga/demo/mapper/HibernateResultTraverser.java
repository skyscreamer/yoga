package org.skyscreamer.yoga.demo.mapper;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.springframework.stereotype.Service;

@Service("resultTraverser")
public class HibernateResultTraverser extends ResultTraverser
{
   @SuppressWarnings("unchecked")
   @Override
   public Class<? extends Object> getClass(Object instance)
   {
      return Hibernate.getClass(instance);
   }

}
