package org.skyscreamer.yoga.demo.mapper;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.mapper.ClassFinderStrategy;

public class HibernateClassFinderStrategy implements ClassFinderStrategy
{
   public Class<?> findClass( Object instance )
   {
      return Hibernate.getClass( instance );
   }
}
