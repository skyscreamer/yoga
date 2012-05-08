package org.skyscreamer.yoga.demo.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GenericDaoBean implements GenericDao
{

    @Autowired
    HibernateTemplate hibernateTemplate;
    
    @Override
    public <T> T find(Class<T> type, long id)
    {
        T entity = hibernateTemplate.load( type, id );
        // Force exception if not found (Hibernate 3 defaults to lazy load.)
        Hibernate.getClass( entity ); 
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findAll(Class<T> type)
    {
        return hibernateTemplate.find( "from " + type.getName() );
    }
}
