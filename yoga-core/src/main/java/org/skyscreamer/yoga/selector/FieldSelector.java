package org.skyscreamer.yoga.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;

public class FieldSelector implements Selector
{
    protected Map<String, FieldSelector> subSelectors = new HashMap<String, FieldSelector>();
    protected EntityConfigurationRegistry _entityConfigurationRegistry;

    public FieldSelector( EntityConfigurationRegistry entityConfigurationRegistry)
    {
        _entityConfigurationRegistry = entityConfigurationRegistry;
    }

    @Override
    public FieldSelector getChildSelector( Class<?> instanceType, String fieldName )
    {
        return getSelector( fieldName );
    }

    public FieldSelector getSelector( String fieldName )
    {
        return subSelectors.get( fieldName );
    }

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        return containsField( property );
    }

    @Override
    public <T> Property<T> getProperty(Class<T> instanceType, String fieldName)
    {
        return new NamedProperty<T>( fieldName );
    }
    
    public boolean containsField( String property )
    {
        return subSelectors.containsKey( property );
    }

    public int getFieldCount()
    {
        return subSelectors.size();
    }

    @Override
    public <T> Collection<Property<T>> getSelectedFields( Class<T> instanceType )
    {
        Set<String> fieldNames = getFieldNames();
        removeNonSupportedFields( instanceType, fieldNames );
        Collection<Property<T>> selected = new ArrayList<Property<T>>();
        for (String name : fieldNames)
        {
            selected.add( new NamedProperty<T>( name ) );
        }
        return selected;
    }

    public <T> void removeNonSupportedFields( Class<T> instanceType, Set<String> fieldNames )
    {
        YogaEntityConfiguration<T> entityConfiguration = _entityConfigurationRegistry.getEntityConfiguration( instanceType );
        if (entityConfiguration != null && entityConfiguration.getSelectableFields() != null)
        {
            for (Iterator<String> iter = fieldNames.iterator(); iter.hasNext();)
            {
                String fieldName = iter.next();
                if (!entityConfiguration.getSelectableFields().contains(fieldName)) {
                    iter.remove();
                }
            }
        }
    }

    public Set<String> getFieldNames()
    {
        return subSelectors.keySet();
    }

    public Map<String, FieldSelector> getFields()
    {
        return subSelectors;
    }

    public void register( String fieldName, FieldSelector subSelector )
    {
        subSelectors.put( fieldName, subSelector );
    }

    @Override
    public <T> Collection<Property<T>> getAllPossibleFields( Class<T> instanceType )
    {
        Collection<Property<T>> allFields = new ArrayList<Property<T>>();
        for (String name : getFieldNames())
        {
            allFields.add( new NamedProperty<T>( name ) );
        }
        return allFields;
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }
}
