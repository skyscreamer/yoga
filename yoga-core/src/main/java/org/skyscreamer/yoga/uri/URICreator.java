package org.skyscreamer.yoga.uri;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.skyscreamer.yoga.populator.ValueReader;

public class URICreator
{
   private static Pattern _uriTemplatePattern = Pattern.compile("\\{[a-zA-Z0-9_]+\\}");

   private List<URIDecorator> decorators;

   public String getHref(String uriTemplate, ValueReader model)
   {
      Matcher matcher = _uriTemplatePattern.matcher(uriTemplate);
      int start = 0;
      StringBuilder href = new StringBuilder();

      while (matcher.find())
      {
         String field = uriTemplate.substring(matcher.start() + 1, matcher.end() - 1);
         href.append(uriTemplate.substring(start, matcher.start())).append(model.getValue(field));
         start = matcher.end();
      }
      href.append(uriTemplate.substring(start));
      return decorate(href.toString());
   }

   protected String decorate(String uri)
   {
      if (decorators == null)
      {
         return uri;
      }

      for (URIDecorator uriDecorator : decorators)
      {
         uri = uriDecorator.decorate(uri);
      }
      return uri;
   }

   public void setDecorators(List<URIDecorator> decorators)
   {
      this.decorators = decorators;
   }

   public List<URIDecorator> getDecorators()
   {
      return decorators;
   }
}
