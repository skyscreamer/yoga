package org.skyscreamer.yoga.demo.populator;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.traverser.ObjectFieldTraverser;
import org.springframework.stereotype.Service;

@Service("objectFieldTraverser")
public class HibernateObjectFieldTraverser extends ObjectFieldTraverser
{

   @SuppressWarnings("unchecked")
   @Override
   public Class<? extends Object> getClass(Object instance)
   {
      return Hibernate.getClass(instance);
   }

}
