package org.skyscreamer.yoga.traverser;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 * Date: 4/21/11
 * Time: 3:07 PM
 */
public class ObjectFieldTraverser 
{
    public void traverse( Object instance, Selector fieldSelector, HierarchicalModel model )
    {
        for ( Method getter : getGetters( getClass(instance) ) )
        {
            String field = getterField( getter );
            try
            {
                if ( fieldSelector.containsField( field, getter ) )
                {
                    Object result = getter.invoke( instance, new Object[0] );

                    if ( isNotBean( getter.getReturnType() ) )
                    {
                    	model.addSimple( field, getter, result );
                    }
                    else if ( Map.class.isAssignableFrom( getter.getReturnType() ) )
                    {
                        HierarchicalModel mapModel = model.createChild( field, getter, result );
                        for ( Map.Entry<?, ?> entry : ((Map<?, ?>) result).entrySet() )
                        {
                            if( isNotBean( getClass( entry.getValue() )) )
                            {
                                mapModel.addSimple( entry.getKey().toString(), getter, entry.getValue() );
                            } 
                            else 
                            {
                                //TODO: what should the selector be in this case?
                                Object value = entry.getValue();
                                HierarchicalModel child = mapModel.createChild( field, getter, value );
                                traverse( value, fieldSelector.getField( field ), child );
                            }
                        }
                    }
                    else if ( Collection.class.isAssignableFrom( getter.getReturnType() ) )
                    {
                    	HierarchicalModel listModel = model.createList( field, getter, result );
                        for ( Object o : (Collection<?>) result )
                        {
                        	if( isNotBean( getClass( o )) )
                        	{
                        		listModel.addSimple( field, getter, result );
                        	} 
                        	else 
                        	{
                        		traverseChild(fieldSelector, listModel, getter, field, o);
                        	}
                        }
                    }
                    else
                    {
                		traverseChild(fieldSelector, model, getter, field, result);
                    }
                }
            } catch ( Exception e )
            {
                throw new RuntimeException( e );
            }
        }
    }

    // allow this to be overriden
   protected void traverseChild(Selector parentSelector, HierarchicalModel parent, AccessibleObject getter,
         String field, Object value)
   {
      traverse( value, parentSelector.getField( field ), parent.createChild( field, getter, value ) );
   }

   protected Class<? extends Object> getClass(Object instance) {
		return instance.getClass();
	}

    private boolean isNotBean( Class<?> clazz )
    {
        return clazz.isPrimitive()
                || clazz.isEnum()
                || Number.class.isAssignableFrom( clazz )
                || String.class.isAssignableFrom( clazz )
                || Boolean.class.isAssignableFrom( clazz )
                || Character.class.isAssignableFrom( clazz );
    }

    private String getterField( Method getter )
    {
        return StringUtils.uncapitalize( getter.getName().substring( 3 ) );
    }


    private List<Method> getGetters( Class<?> clazz )
    {
        List<Method> methodList = new ArrayList<Method>();
        for ( Method method : clazz.getMethods() )
        {
            if ( isGetter( method ) )
            {
                methodList.add( method );
            }
        }
        return methodList;
    }

    public static boolean isGetter( Method method )
    {
        return (method.getName().startsWith( "get" )
                && method.getName().length() > 3
                && method.getParameterTypes().length == 0
                && !void.class.equals( method.getReturnType() ));
    }

    protected Map<String, Object> convertToMap( HashMap<String, Object> dto )
    {
        return dto;
    }
}
