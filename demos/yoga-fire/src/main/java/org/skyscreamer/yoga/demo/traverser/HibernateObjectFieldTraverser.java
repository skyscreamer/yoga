package org.skyscreamer.yoga.demo.traverser;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.touri.ObjectToURI;
import org.skyscreamer.yoga.demo.annotations.Nested;
import org.skyscreamer.yoga.demo.annotations.Reference;
import org.skyscreamer.yoga.demo.view.NameUtil;
import org.skyscreamer.yoga.selector.CombinedSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.DefinedSelectorImpl;
import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.traverser.HierarchicalModel;
import org.skyscreamer.yoga.traverser.ObjectFieldTraverser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("objectFieldTraverser")
public class HibernateObjectFieldTraverser extends ObjectFieldTraverser
{

   @SuppressWarnings("unchecked")
   @Override
   public Class<? extends Object> getClass(Object instance)
   {
      return Hibernate.getClass(instance);
   }

   @Override
   protected void traverseChild(Selector parentSelector, HierarchicalModel model,
         AccessibleObject getter, String field, String name, Object value)
   {
      if (getter.isAnnotationPresent(Reference.class))
      {
         Reference reference = getter.getAnnotation(Reference.class);
         Selector childSelector = parentSelector.getField(field);

         if (!isUserDefinedSelector(childSelector))
         {
            // if no user defined selector is found, clear
            String selectorStr = reference.selector();
            if (StringUtils.hasText(selectorStr))
            {
               try
               {
                  childSelector = SelectorParser.parse(selectorStr);
               }
               catch (ParseSelectorException e)
               {
                  throw new RuntimeException(e);
               }
            }
            else
            {
               childSelector = new CoreSelector();
            }
         }
         HierarchicalModel childModel = addHref(model, getter, field, value);
         traverse(value, childSelector, childModel);
      }
      else
      {
         // TODO: What should we do by default if this is URIable?
         String uri = getUri(value);
         HierarchicalModel nextModel = (uri == null) ? model.createChild(name, getter, value) : addHref(model, getter, name, value);
         traverse(value, parentSelector.getField(field), nextModel);
      }
   }
   
   @Override
   protected void traverseList(Selector fieldSelector, HierarchicalModel model, Method getter,
         String field, Collection<?> list)
   {
      HierarchicalModel listModel = model.createList(field, getter, list);
      for (Object o : list)
      {
         Nested nested = getter.getAnnotation(Nested.class);
         String name = (nested != null) ? nested.childName() : NameUtil.getName(o.getClass());

         if (isNotBean(getClass(o)))
         {
            listModel.addSimple(name, getter, list);
         }
         else
         {
            traverseChild(fieldSelector, listModel, getter, field, name, o);
         }
      }
   }

   protected HierarchicalModel addHref(HierarchicalModel model, AccessibleObject getter,
         String field, Object value)
   {
      HierarchicalModel childModel = model.createChild(field, getter, value);
      childModel.addSimple("href", null, getUri(value));
      return childModel;
   }

   protected String getUri(Object value)
   {
      if (!value.getClass().isAnnotationPresent(Entity.class))
      {
         return null;
      }
      String mainUri = ObjectToURI.getInstance().resolveURI(value);
      HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
      String uri = request.getRequestURI();
      int lastIndex = uri.lastIndexOf('.');
      String extension = lastIndex == -1 ? "" : ("." + uri.substring(lastIndex + 1));
      return mainUri + extension;
   }

   private boolean isUserDefinedSelector(Selector selector)
   {
      if (selector instanceof DefinedSelectorImpl)
      {
         DefinedSelectorImpl impl = (DefinedSelectorImpl) selector;
         return !impl.getFields().isEmpty();
      }
      else if (selector instanceof CombinedSelector)
      {
         for (Selector sel : ((CombinedSelector) selector).getChildren())
         {
            if (isUserDefinedSelector(sel))
            {
               return true;
            }
         }
      }
      return false;
   }

}
