package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
import java.util.*;

public class CoreSelector implements Selector
{
   @Override
   public Selector getField(PropertyDescriptor property)
   {
      return this;
   }

   @Override
   public Selector getField(String fieldName)
   {
      return this;
   }

   @Override
   public boolean containsField( PropertyDescriptor property, FieldPopulator<?> fieldPopulator )
   {
       boolean result = property.getReadMethod().isAnnotationPresent( Core.class );
       if ( result == false )
       {
           if ( fieldPopulator != null )
           {
               result = fieldPopulator.getCoreFields().contains( property.getName() );
           }
       }
       return result;
   }

   @Override
   public boolean containsField(String property)
   {
      return false;
   }

   @Override
   public Set<String> getFieldNames()
   {
      return Collections.emptySet();
   }

    @Override
    public Map<String, Selector> getFields() {
        return Collections.emptyMap();
    }
}
