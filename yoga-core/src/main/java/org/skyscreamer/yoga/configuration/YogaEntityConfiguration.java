package org.skyscreamer.yoga.configuration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.skyscreamer.yoga.annotations.ExtraField;
import org.skyscreamer.yoga.exceptions.YogaRuntimeException;
import org.skyscreamer.yoga.selector.Property;

/**
 * Interface for a class that configures an entity.  An implementation of this interface must return the class being
 * configured.  It can be used instead of or in conjunction with entity-level annotations.
 *
 * @see SimpleYogaEntityConfiguration
 * @see org.skyscreamer.yoga.annotations.Core
 */
public abstract class YogaEntityConfiguration<T> {
    private volatile Class<T> _instanceClass = null;

    @SuppressWarnings("unchecked")
    public YogaEntityConfiguration()
    {
        try {
            _instanceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        catch (ClassCastException e) {
            throw new YogaRuntimeException("Unable to initialize class " + getClass().getName() + " because " +
                    "entity class could not be determined.  Either specify it in the generic type when extending " +
                    "YogaEntityConfiguration, or explicitly override getEntityClass() with the correct value.");
        }
    }
    /**
     * Identifies the class supported by this configuration.
     *
     * @return A class object for the entity being configured
     */
    public Class<T> getEntityClass() {
        return _instanceClass;
    }

    /**
     * Returns a collection of core fields for an entity.  Core fields are returned by default, and do not require a
     * selector.  If an empty collection is returned no fields are considered core.  If a null is returned, this setting
     * will be ignored and the entity will be analyzed for core annotations.  Except in the case of a null being
     * returned, this configuration overrides (rather than extends) the entity configuration.
     *
     * @return The collection of core field names
     */
    public Collection<String> getCoreFields() {
        return null;
    }

    /**
     * Returns a collection of fields that can be selected on an entity  similar to getSelectableFields().  
     * This gives you the opportunity to use a serialization method other than reflection.
     * 
     * @return The collection of Property objects used for serialization
     * @see Property
     */
    public Collection<Property<T>> getProperties() {
        return null;
    }

    /**
     * Returns a collection of fields that can be selected on an entity.  If an empty collection is return, nothing can
     * be selected.  If a null is returned, this setting is ignored, and all getters on an entity and extra fields can
     * be selected.
     *
     * @return
     */
    public Collection<String> getSelectableFields() {
        return null;
    }

    /**
     * Specifies the template for the URL represented by this entity.  This overrides any definition of @URITemplate
     * in the entity itself.  If this method returns null, it will default back to entity-specified behavior.
     *
     * @return A URITemplate, or null if the entity should be examined for the template value.
     */
    public String getURITemplate() {
        return null;
    }

    /**
     * This method returns a list of valid extra field methods on this object.
     *
     * A valid field must:
     *     1. Be annotated with @ExtraField
     *     2. Take either no parameters, or a single parameter with the entity supported by the configuration
     *
     * @return A list of method objects
     */
    public List<Method> getExtraFieldMethods()
    {
        return getExtraFieldMethods( new ArrayList<Method>(), getClass() );
    }

    private List<Method> getExtraFieldMethods(List<Method> result, Class<?> current)
    {
        for ( Method method : current.getDeclaredMethods() )
        {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if ( method.isAnnotationPresent( ExtraField.class )
                    && (parameterTypes.length == 0
                    || (parameterTypes.length == 1
                    && parameterTypes[0].equals( getEntityClass() ))))
            {
                result.add( method );
            }
        }
        if(current.getSuperclass() != null)
        {
            getExtraFieldMethods( result, current.getSuperclass() );
        }
        return result;
    }

    /**
     * This method returns a list of the field names for @ExtraField methods in this object.
     *
     * @return A list of string field names
     */
    public List<String> getExtraFieldNames()
    {
        List<String> result = new ArrayList<String>();

        for (Method method : getExtraFieldMethods())
        {
            ExtraField extraField = method.getAnnotation( ExtraField.class );
            result.add( extraField.value() );
        }

        return result;
    }
}
