package org.skyscreamer.yoga.populator;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.selector.Selector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public abstract class AbstractFieldPopulator<M, D> implements FieldPopulator<M, D>
{
    protected Class<D> _dtoClass = (Class<D>)((ParameterizedType)getClass().getGenericSuperclass())
            .getActualTypeArguments()[1];

    public D populateObjectFields( M model, Selector selector )
    {
        D result;
        try
        {
            Constructor<D> constructor = _dtoClass.getConstructor();
            result = constructor.newInstance();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Default constructor required for " + _dtoClass );
        }

        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors( result.getClass() );
        for ( PropertyDescriptor property : properties )
        {
            Method writeMethod = property.getWriteMethod();
            if ( writeMethod != null )
            {
                try
                {
                    writeMethod.invoke( result, constructFieldValue( property, model ) );
                }
                catch ( Exception e )
                {
                    throw new RuntimeException( "Could not invoke method " + writeMethod.getName() + " on " +
                            _dtoClass );
                }
            }
        }

        return result;
    }

    protected abstract Object constructFieldValue( PropertyDescriptor property, M model );

    public List<D> populateListFields( List<M> models, Selector selector )
    {
        List<D> result = new ArrayList<D>();
        for ( M model : models )
        {
            result.add( populateObjectFields( model, selector ) );
        }
        return result;
    }
}
