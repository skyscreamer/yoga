package org.skyscreamer.yoga.demo.dto;

import java.util.List;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;
import org.skyscreamer.yoga.demo.model.User;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA. User: corby
 */
@Service
public class UserFieldPopulator implements FieldPopulator<User>
{
   @Autowired
   GenericDao _genericDao;

   @Override
   public void addExtraFields(Selector selector, User model, ResultTraverser traverser,
         HierarchicalModel output)
   {
      if ( selector.containsField( "recommendedAlbums" ) )
      {
         List<Album> allAlbums = _genericDao.findAll( Album.class );
         Selector childSelector = selector.getField( "recommendedAlbums" );
         traverser.traverseIterable( childSelector, output, "recommendedAlbums", allAlbums );
      }
   }
}
