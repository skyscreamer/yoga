package org.skyscreamer.yoga.resteasy.view;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;

import org.skyscreamer.yoga.mapper.ResultMapper;
import org.skyscreamer.yoga.selector.CombinedSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractSelectorMessageBodyWriter implements MessageBodyWriter<Object>,
      ApplicationContextAware
{
   protected ResultMapper _fieldPopulator;

   @Override
   public long getSize(Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4)
   {
      return -1;
   }

   @Override
   public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3)
   {
      return true;
   }

   protected Selector getSelector(HttpServletRequest req)
   {
      CoreSelector coreSelector = new CoreSelector();
      String selectorStr = req.getParameter("selector");
      if (selectorStr != null)
      {
         try
         {
            return new CombinedSelector(SelectorParser.parse(selectorStr), coreSelector);
         }
         catch (ParseSelectorException e)
         {
            // TODO: Add logging here. Spring spits out
            // "no matching editors or conversion strategy found", which
            // is vague and misleading. (ie, A URL typo looks like a
            // configuration error)
            throw new IllegalArgumentException("Could not parse selector", e);
         }
      }
      else
      {
         return coreSelector;
      }
   }
   

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
   {
      this._fieldPopulator = applicationContext.getBean(ResultMapper.class);
   }
   
   protected ResultTraverser getTraverser()
   {
      return _fieldPopulator.getResultTraverser();
   }

}
