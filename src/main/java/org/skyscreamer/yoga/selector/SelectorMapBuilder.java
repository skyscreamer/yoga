package org.skyscreamer.yoga.selector;

import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 4/18/11
 * Time: 8:26 PM
 */
public class SelectorMapBuilder
{
    public Map<String, Object> convertIntoTree( Object object, Selector selector )
    {
        Map<String, Object> objectTree = new HashMap<String, Object>();

        Map<String, SelectorField> selectorFieldMap = new HashMap<String, SelectorField>();
        if ( selector != null )
        {
            for ( SelectorField selectorField : selector.getFields() )
            {
                selectorFieldMap.put( selectorField.getFieldName(), selectorField );
            }
        }

        List<Method> getters = getGetters( object.getClass() );
        for ( Method getter : getters )
        {
            String field = getterField( getter );
            try
            {
                if ( selectorFieldMap.containsKey( field ) || isCore( getter ) )
                {
                    if ( isNotBean( getter.getReturnType() ) )
                    {
                        objectTree.put( field, getter.invoke( object, new Object[0] ) );
                    }
                    else if ( Collection.class.isAssignableFrom( getter.getReturnType() ) )
                    {
                        List<Object> listField = new ArrayList<Object>();
                        objectTree.put( field, listField );
                        for ( Object o : (Collection) getter.invoke( object, new Object[0] ) )
                        {
                            Object value = isNotBean( o.getClass() ) ? o
                                    : convertIntoTree( o, selectorFieldMap.get( field ).getSelector() );
                            listField.add( value );
                        }
                    }
                    else
                    {
                        objectTree.put( field, convertIntoTree( getter.invoke( object, new Object[0] ),
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

    private boolean isNotBean( Class clazz )
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


    private List<Method> getGetters( Class clazz )
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

    private boolean isCore( Method accessorMethod )
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
}
