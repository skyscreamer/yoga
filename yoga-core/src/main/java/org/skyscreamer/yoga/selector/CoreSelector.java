package org.skyscreamer.yoga.selector;

import org.skyscreamer.yoga.annotations.Core;
import org.skyscreamer.yoga.populator.FieldPopulator;

import java.beans.PropertyDescriptor;
<<<<<<< HEAD
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
=======
import java.util.*;
>>>>>>> upstream/master

public class CoreSelector implements Selector
{
    @Override
    public Selector getField(String fieldName)
    {
        return this;
    }

    @Override
    public boolean containsField(PropertyDescriptor property, FieldPopulator fieldPopulator)
    {
        Method readMethod = property.getReadMethod();
        boolean isCore = readMethod.isAnnotationPresent( Core.class );
        if (!isCore && fieldPopulator != null)
        {
            isCore = fieldPopulator.getCoreFields().contains( property.getName() );
        }
        return isCore;
    }

    @Override
    public boolean containsField(String property)
    {
        return false;
    }

<<<<<<< HEAD
    @Override
    public Set<String> getFieldNames()
    {
        return new HashSet<String>();
=======
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
>>>>>>> upstream/master
    }
}
