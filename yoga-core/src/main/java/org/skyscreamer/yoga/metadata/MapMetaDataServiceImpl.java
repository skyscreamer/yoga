package org.skyscreamer.yoga.metadata;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Property;
import org.skyscreamer.yoga.util.NameUtil;
import org.skyscreamer.yoga.util.ObjectUtil;

public class MapMetaDataServiceImpl implements MetaDataService
{

    private Map<String, Class<?>> _typeMappings;
    private Map<Class<?>, String> _typeToStringMap = new HashMap<Class<?>, String>();

    private String rootMetaDataUrl;
    private CoreSelector _coreSelector;

    public void setCoreSelector( CoreSelector coreSelector )
    {
        this._coreSelector = coreSelector;
    }

    public CoreSelector getCoreSelector()
    {
        return _coreSelector;
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
    public Collection<String> getTypes()
    {
        return _typeMappings.keySet();
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
        return result;
    }

    protected void addCoreFields( Class<?> type, String suffix, TypeMetaData result )
    {
        Collection<Property> allFields = _coreSelector.getAllPossibleFields( type );
        Set<String> coreFieldName = new HashSet<String>();

        for (Property property : _coreSelector.getSelectedFields( type, null ))
        {
            coreFieldName.add( property.name() );
        }

        for ( Property property : allFields )
        {
            Method readMethod = property.getReadMethod();
            Class<?> propertyType = readMethod.getReturnType();
            PropertyMetaData propertyMetaData = new PropertyMetaData();

            propertyMetaData.setName( property.name() );
            propertyMetaData.setIsCore( coreFieldName.contains( property.name() ) );

            if ( ObjectUtil.isPrimitive( propertyType ) )
            {
                propertyMetaData.setType( propertyType == String.class ? "String" : propertyType
                        .getName() );
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

            result.getPropertyMetaData().add( propertyMetaData );
        }
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
    public String getMetadataHref(Class<?> propertyType, String suffix)
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
        String href = getMetadataHref( propertyType, suffix );
        if (href != null)
        {
            propertyMetaData.setHref( href );
        }
    }

}
