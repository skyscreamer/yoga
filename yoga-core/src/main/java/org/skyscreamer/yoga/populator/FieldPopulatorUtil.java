package org.skyscreamer.yoga.populator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FieldPopulatorUtil
{
   public static List<Method> getPopulatorExtraFieldMethods(FieldPopulator<?> populator,
         Class<?> instanceType)
   {
      List<Method> result = new ArrayList<Method>();
      if (populator != null)
      {
         for (Method method : populator.getClass().getDeclaredMethods())
         {
            if (method.isAnnotationPresent(ExtraField.class))
            {
               Class<?>[] parameterTypes = method.getParameterTypes();
               if (parameterTypes.length == 0
                     || (parameterTypes.length == 1 && parameterTypes[0].equals(instanceType)))
               {
                  result.add(method);
               }
            }
         }
      }
      return result;
   }

}
