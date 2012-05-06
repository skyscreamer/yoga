package org.skyscreamer.yoga.demo.mapper;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.util.ClassFinderStrategy;

public class HibernateClassFinderStrategy implements ClassFinderStrategy
{
    public Class<?> findClass( Object instance )
    {
        return Hibernate.getClass( instance );
    }
}
