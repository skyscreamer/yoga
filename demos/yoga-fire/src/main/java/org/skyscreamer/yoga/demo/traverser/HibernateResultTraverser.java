package org.skyscreamer.yoga.demo.traverser;

import java.beans.PropertyDescriptor;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.touri.ObjectToURI;
import org.skyscreamer.yoga.demo.annotations.Nested;
import org.skyscreamer.yoga.demo.annotations.Reference;
import org.skyscreamer.yoga.demo.util.NameUtil;
import org.skyscreamer.yoga.selector.CombinedSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.DefinedSelectorImpl;
import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("resultTraverser")
public class HibernateResultTraverser extends ResultTraverser
{
   @SuppressWarnings("unchecked")
   @Override
   public Class<? extends Object> getClass(Object instance)
   {
      return Hibernate.getClass(instance);
   }

   @Override
   protected void traverseChild(Selector parentSelector, HierarchicalModel model,
         PropertyDescriptor property, String name, Object value)
   {
      Reference reference = property.getReadMethod().getAnnotation(Reference.class);
      if (reference != null)
      {
         Selector childSelector = parentSelector.getField(property);

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
         HierarchicalModel childModel = addHref(model, property, value);
         traverse(value, childSelector, childModel);
      }
      else
      {
         // TODO: What should we do by default if this is URIable?
         String uri = getUri(value);
         HierarchicalModel nextModel = (uri == null) ? model.createChild(property, value)
               : addHref(model, property, value);
         traverse(value, parentSelector.getField(property), nextModel);
      }
   }

   @Override
   protected void traverseIterable(Selector fieldSelector, HierarchicalModel model,
         PropertyDescriptor property, Iterable<?> list)
   {
      HierarchicalModel listModel = model.createList(property, list);
      for (Object o : list)
      {
         Nested nested = property.getReadMethod().getAnnotation(Nested.class);
         String name = (nested != null) ? nested.childName() : NameUtil.getName(o.getClass());

         if (isNotBean(getClass(o)))
         {
            listModel.addSimple(property, list);
         }
         else
         {
            traverseChild(fieldSelector, listModel, property, name, o);
         }
      }
   }

   protected HierarchicalModel addHref(HierarchicalModel model, PropertyDescriptor property,
         Object value)
   {
      HierarchicalModel childModel = model.createChild(property, value);
      childModel.addSimple("href", getUri(value));
      return childModel;
   }

   protected String getUri(Object value)
   {
      // this is specific to this use case. There can be other logic to
      // determine URIable eligibility
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
