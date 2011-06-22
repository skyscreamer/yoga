package org.skyscreamer.yoga.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GenericDaoBean extends HibernateDaoSupport implements GenericDao {

	@Override
	public <T> T find(Class<T> type, long id) {
		return getHibernateTemplate().load(type, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(Class<T> type) {
		return getHibernateTemplate().find("from " + type.getName());
	}

    @Autowired
    public void setSession(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
    

}
