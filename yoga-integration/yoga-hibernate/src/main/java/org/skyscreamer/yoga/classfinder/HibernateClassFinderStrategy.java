package org.skyscreamer.yoga.classfinder;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.util.ClassFinderStrategy;

/**
 * User: corby
 * Date: 8/28/12
 */
public class HibernateClassFinderStrategy implements ClassFinderStrategy
{
    @SuppressWarnings("unchecked")
	public <T> Class<T> findClass( T instance )
    {
        return Hibernate.getClass( instance );
    }
}
