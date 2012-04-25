package org.skyscreamer.yoga.metadata;

import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.populator.ExtraField;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.Core;
import org.skyscreamer.yoga.util.NameUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.skyscreamer.yoga.populator.FieldPopulatorUtil.getPopulatorExtraFieldMethods;

public class MapMetaDataServiceImpl implements MetaDataService
{

    private Map<String, Class<?>> _typeMappings;
    private Map<Class<?>, String> _typeToStringMap = new HashMap<Class<?>, String>();

    private String rootMetaDataUrl;
    private FieldPopulatorRegistry _fieldPopulatorRegistry;

    public FieldPopulatorRegistry getFieldPopulatorRegistry()
    {
        return _fieldPopulatorRegistry;
    }

    public void setFieldPopulatorRegistry( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        _fieldPopulatorRegistry = fieldPopulatorRegistry;
    }

    public String getRootMetaDataUrl()
    {
        return rootMetaDataUrl;
    }

    public void setRootMetaDataUrl( String rootMetaDataUrl )
    {
        this.rootMetaDataUrl = rootMetaDataUrl;
    }

    public void setTypeMappings( Map<String, Class<?>> map )
    {
        this._typeMappings = map;
        _typeToStringMap.clear();
        for ( Entry<String, Class<?>> entry : map.entrySet() )
        {
            _typeToStringMap.put( entry.getValue(), entry.getKey() );
        }
    }

    @Override
    public Map<String, Class<?>> getTypeMappings()
    {
        return _typeMappings;
    }

    @Override
    public Class<?> getTypeForName( String name )
    {
        return _typeMappings.get( name );
    }

    /**
     * given a type, get a name. This takes subclassing into consideration. For
     * now, this will return the first subclass match to the type, not the
     * closest
     */
    @Override
    public String getNameForType( Class<?> type )
    {
        if ( _typeToStringMap.containsKey( type ) )
        {
            return _typeToStringMap.get( type );
        }

        for ( Entry<String, Class<?>> entry : _typeMappings.entrySet() )
        {
            if ( entry.getValue().isAssignableFrom( type ) )
            {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public TypeMetaData getMetaData( String name, String suffix )
    {
        return getMetaData( getTypeForName( name ), suffix );
    }

    @Override
    public TypeMetaData getMetaData( Class<?> type, String suffix )
    {
        TypeMetaData result = new TypeMetaData();
        result.setName( NameUtil.getFormalName( type ) );
        addCoreFields( type, suffix, result );
        addPopulatorFields( type, suffix, result );
        return result;
    }

    protected void addCoreFields( Class<?> type, String suffix, TypeMetaData result )
    {
        for ( PropertyDescriptor property : PropertyUtil.getReadableProperties( type ) )
        {
            Method readMethod = property.getReadMethod();
            String name = property.getName();
            boolean core = readMethod.isAnnotationPresent( Core.class );

            addField( suffix, result, readMethod, name, core );
        }
    }

    protected void addPopulatorFields( Class<?> type, String suffix, TypeMetaData result )
    {
        FieldPopulator<?> fieldPopulator = null;
        if ( _fieldPopulatorRegistry != null )
        {
            fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( type );
        }

        if ( fieldPopulator == null )
        {
            return;
        }

        for ( Method method : getPopulatorExtraFieldMethods( fieldPopulator, type ) )
        {
            String name = method.getAnnotation( ExtraField.class ).value();
            addField( suffix, result, method, name, false );
        }
    }

    protected PropertyMetaData addField( String suffix, TypeMetaData result, Method readMethod,
            String name, boolean core )
    {
        Class<?> propertyType = readMethod.getReturnType();
        PropertyMetaData propertyMetaData = new PropertyMetaData();
        result.getPropertyMetaData().add( propertyMetaData );

        propertyMetaData.setName( name );
        propertyMetaData.setIsCore( core );

        if ( ResultTraverser.isPrimitive( propertyType ) )
        {
            propertyMetaData.setType( propertyType == String.class ? "String" : propertyType.getName() );
        }
        else if ( Iterable.class.isAssignableFrom( propertyType ) || propertyType.isArray() )
        {
            Class<?> collectionValueType = getCollectionType( readMethod, propertyType );
            String typeName = NameUtil.getFormalName( collectionValueType ) + "[]";
            propertyMetaData.setType( typeName );
            addHref( propertyMetaData, collectionValueType, suffix );
        }
        else
        {
            propertyMetaData.setType( NameUtil.getFormalName( propertyType ) );
            addHref( propertyMetaData, propertyType, suffix );
        }

        return propertyMetaData;
    }

    protected Class<?> getCollectionType( Method readMethod, Class<?> propertyType )
    {
        Class<?> collectionValueType = null;
        if ( propertyType.isArray() )
        {
            collectionValueType = propertyType.getComponentType();
        }
        else
        {
            Type genericReturnType = readMethod.getGenericReturnType();
            if ( genericReturnType instanceof ParameterizedType )
            {
                ParameterizedType zType = (ParameterizedType) genericReturnType;
                collectionValueType = (Class<?>) zType.getActualTypeArguments()[0];
            }
        }
        return collectionValueType;
    }

    @Override
    public String getHref( Class<?> propertyType, String suffix )
    {
        String nameForType = getNameForType( propertyType );
        if ( nameForType != null )
        {
            return getRootMetaDataUrl() + nameForType + "." + suffix;
        }
        else
        {
            return null;
        }
    }

    protected void addHref( PropertyMetaData propertyMetaData, Class<?> propertyType, String suffix )
    {
        String href = getHref( propertyType, suffix );
        if ( href != null )
        {
            propertyMetaData.setHref( href );
        }
    }

}
