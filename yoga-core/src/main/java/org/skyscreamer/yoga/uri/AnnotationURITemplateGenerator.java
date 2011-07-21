package org.skyscreamer.yoga.uri;

import org.skyscreamer.yoga.annotations.URITemplate;

public class AnnotationURITemplateGenerator implements URITemplateGenerator
{

   @Override
   public String getTemplate(Object instance, Class<?> type)
   {
      if (type.isAnnotationPresent(URITemplate.class))
         return type.getAnnotation(URITemplate.class).value();
      else 
         return null;
   }

}
