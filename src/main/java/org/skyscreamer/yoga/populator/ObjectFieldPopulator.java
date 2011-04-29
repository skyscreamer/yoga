package org.skyscreamer.yoga.populator;

import org.apache.commons.lang.StringUtils;
import org.skyscreamer.yoga.selector.Core;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 * Date: 4/21/11
 * Time: 3:07 PM
 */
public class ObjectFieldPopulator extends AbstractFieldPopulator<Object,HashMap<String,Object>>
{
    protected HashMap<String,Object> convertToDto( Object instance, Selector fieldSelector )
    {
        HashMap<String, Object> objectTree = new HashMap<String, Object>();

        Map<String, SelectorField> selectorFieldMap = new HashMap<String, SelectorField>();
        if ( fieldSelector != null )
        {
            for ( SelectorField selectorField : fieldSelector.getFields() )
            {
                selectorFieldMap.put( selectorField.getFieldName(), selectorField );
            }
        }

        List<Method> getters = getGetters( getClass(instance) );
        for ( Method getter : getters )
        {
            String field = getterField( getter );
            try
            {
                if ( selectorFieldMap.containsKey( field ) || isRenderedByDefault( getter ) )
                {
                    if ( isNotBean( getter.getReturnType() ) )
                    {
                        objectTree.put( field, getter.invoke( instance, new Object[0] ) );
                    }
                    else if ( Collection.class.isAssignableFrom( getter.getReturnType() ) )
                    {
                        List<Object> listField = new ArrayList<Object>();
                        objectTree.put( field, listField );
                        for ( Object o : (Collection<?>) getter.invoke( instance, new Object[0] ) )
                        {
                            Object value = isNotBean( getClass(o) ) ? o
                                    : convertToDto( o, selectorFieldMap.get( field ).getSelector() );
                            listField.add( value );
                        }
                    }
                    else
                    {
                        objectTree.put( field, convertToDto( getter.invoke( instance, new Object[0] ),
                                selectorFieldMap.get( field ).getSelector() ) );
                    }
                }
            } catch ( Exception e )
            {
                throw new RuntimeException( e );
            }
        }
        return objectTree;
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
        Method[] methods = clazz.getMethods();
        List<Method> methodList = new ArrayList<Method>( methods.length );
        for ( Method method : methods )
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

    private boolean isRenderedByDefault( Method accessorMethod )
    {
        boolean result = false;
        for ( Annotation annotation : accessorMethod.getDeclaredAnnotations() )
        {
            if ( annotation instanceof Core )
            {
                result = true;
            }
        }
        return result;
    }

    protected Map<String, Object> convertToMap( HashMap<String, Object> dto )
    {
        return dto;
    }
}
