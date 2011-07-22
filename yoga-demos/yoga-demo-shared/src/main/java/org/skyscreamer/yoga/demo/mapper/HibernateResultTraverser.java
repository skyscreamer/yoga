package org.skyscreamer.yoga.demo.mapper;

import org.hibernate.Hibernate;
import org.skyscreamer.yoga.demo.dto.UserFieldPopulator;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resultTraverser")
public class HibernateResultTraverser extends ResultTraverser
{
   @Autowired
   public void addPopulator(UserFieldPopulator userFieldPopulator)
   {
      getFieldPopulators().put( User.class, userFieldPopulator );
   }

   @SuppressWarnings("unchecked")
   @Override
   public Class<? extends Object> getClass(Object instance)
   {
      return Hibernate.getClass( instance );
   }

}
