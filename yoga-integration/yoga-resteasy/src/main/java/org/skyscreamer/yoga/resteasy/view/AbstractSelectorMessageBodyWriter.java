package org.skyscreamer.yoga.resteasy.view;

import org.skyscreamer.yoga.mapper.ResultMapper;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

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
      String selectorString = req.getParameter("selector");
      return SelectorParser.parseSelector( selectorString );
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
