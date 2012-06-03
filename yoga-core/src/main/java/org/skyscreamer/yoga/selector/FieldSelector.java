package org.skyscreamer.yoga.selector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.skyscreamer.yoga.annotations.SupportedFields;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;

public class FieldSelector implements Selector
{
    protected Map<String, FieldSelector> subSelectors = new HashMap<String, FieldSelector>();
    protected FieldPopulatorRegistry _fieldPopulatorRegistry;

    public FieldSelector( FieldPopulatorRegistry fieldPopulatorRegistry )
    {
        _fieldPopulatorRegistry = fieldPopulatorRegistry;
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

    public boolean containsField( String property )
    {
        return subSelectors.containsKey( property );
    }

    public int getFieldCount()
    {
        return subSelectors.size();
    }

    @Override
    public Collection<Property> getSelectedFields( Class<?> instanceType, Object instance )
    {
        Set<String> fieldNames = getFieldNames();
        removeNonSupportedFields( instanceType, fieldNames );
        Collection<Property> selected = new ArrayList<Property>();
        for (String name : fieldNames)
        {
            selected.add( new NamedProperty( name ) );
        }
        return selected;
    }

    @SuppressWarnings("unchecked")
    public void removeNonSupportedFields( Class<?> instanceType, Set<String> fieldNames )
    {
        Object fieldPopulator = _fieldPopulatorRegistry.getFieldPopulator( instanceType );
        if (fieldPopulator != null)
        {
            try
            {
                for (Method method : fieldPopulator.getClass().getMethods())
                {
                    if (method.isAnnotationPresent( SupportedFields.class ))
                    {
                        List<String> supportedFields = (List<String>) method
                                .invoke( fieldPopulator );
                        for (Iterator<String> iter = fieldNames.iterator(); iter.hasNext();)
                        {
                            String fieldName = iter.next();
                            if (!supportedFields.contains( fieldName ))
                            {
                                iter.remove();
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                // @SupportedFields not found on method of return type
                // List<String>
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
    public Collection<Property> getAllPossibleFields( Class<?> instanceType )
    {
        Collection<Property> allFields = new ArrayList<Property>();
        for (String name : getFieldNames())
        {
            allFields.add( new NamedProperty( name ) );
        }
        return allFields;
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }
}
