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

public class DefaultMetaDataRegistry implements MetaDataRegistry
{
    private Map<String, Class<?>> _typeMappings = new HashMap<String, Class<?>>();
    private Map<Class<?>, String> _typeToStringMap = new HashMap<Class<?>, String>();

    private String rootMetaDataUrl;
    private CoreSelector _coreSelector;

    public void setCoreSelector( CoreSelector coreSelector )
    {
        this._coreSelector = coreSelector;
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


    public void registerTypeMapping( String name, Class<?> type )
    {
        _typeMappings.put( name, type );
        _typeToStringMap.put( type, name );
    }

    public void registerClasses( Class<?> ... types )
    {
        setRegisteredClasses( types );
    }

    public void registerClasses( Collection<Class<?>> types )
    {
        setRegisteredClasses( types.toArray( new Class<?>[types.size()] ) );
    }

    public void setRegisteredClasses( Class<?> ... types )
    {
        for( Class<?> type : types )
        {
            registerTypeMapping( NameUtil.getName( type ), type );
        }
    }


    @Override
    public Collection<String> getTypes()
    {
        return _typeMappings.keySet();
    }

    private Class<?> getTypeForName( String name )
    {
        return _typeMappings.get( name );
    }

    /**
     * given a type, get a name. This takes subclassing into consideration. For
     * now, this will return the first subclass match to the type, not the closest
     */
    private String getNameForType( Class<?> type )
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

    private TypeMetaData getMetaData( Class<?> type, String suffix )
    {
        TypeMetaData result = new TypeMetaData();
        result.setName( NameUtil.getFormalName( type ) );
        addFields( type, suffix, result );
        return result;
    }

    protected <T> void addFields( Class<T> type, String suffix, TypeMetaData result )
    {
        Map<String, Property<T>> allFields = _coreSelector.getAllPossibleFieldMap( type );
        Set<String> coreFieldName = new HashSet<String>();

        for (Property<T> property : _coreSelector.getSelectedFields( type ))
        {
            coreFieldName.add( property.name() );
        }

        for ( Property<T> property : allFields.values() )
        {
            Method readMethod = property.getReadMethod();
            Class<?> propertyType = readMethod.getReturnType();
            PropertyMetaData propertyMetaData = new PropertyMetaData();

            String name = property.name();
            propertyMetaData.setName( name );
            propertyMetaData.setIsCore( coreFieldName.contains( name ) );

            if ( property.isPrimitive() )
            {
                propertyMetaData.setType( propertyType == String.class ? "String" : propertyType.getName() );
            }
            else if ( Iterable.class.isAssignableFrom( propertyType ) || propertyType.isArray() )
            {
                Class<?> collectionValueType = getCollectionType( readMethod, propertyType );
				String typeName;
				if ( collectionValueType == null )
				{
					typeName = "Collection";
				}
				else
				{
					typeName = "Collection<" + NameUtil.getFormalName(collectionValueType) + ">";
				}
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
            return rootMetaDataUrl + nameForType + "." + suffix;
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
