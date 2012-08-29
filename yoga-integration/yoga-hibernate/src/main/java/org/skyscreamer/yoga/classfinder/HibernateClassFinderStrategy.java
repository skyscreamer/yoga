package org.skyscreamer.yoga.classfinder;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.util.ClassFinderStrategy;

/**
 * User: corby
 * Date: 8/28/12
 */
public class HibernateClassFinderStrategy implements ClassFinderStrategy
{
    public Class<?> findClass( Object instance )
    {
        return Hibernate.getClass( instance );
    }
}
