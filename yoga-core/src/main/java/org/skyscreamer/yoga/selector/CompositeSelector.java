package org.skyscreamer.yoga.selector;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class CompositeSelector implements Selector
{
    private Selector coreSelector;
    private Selector fieldSelector;

    public CompositeSelector( Selector coreSelector, Selector fieldSelector )
    {
        this.coreSelector = coreSelector;
        this.fieldSelector = fieldSelector;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        Selector fieldSelectorChild = fieldSelector.getChildSelector( instanceType, fieldName );
        Selector coreSelectorChild = coreSelector.getChildSelector( instanceType, fieldName );
        if (fieldSelectorChild == null)
        {
            return coreSelectorChild;
        }
        else
        {
            return new CompositeSelector( coreSelectorChild, fieldSelectorChild );
        }
    }

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        return coreSelector.containsField( instanceType, property )
                || fieldSelector.containsField( instanceType, property );
    }

    @Override
    public <T> Property<T> getProperty(Class<T> instanceType, String fieldName)
    {
    	Property<T> property = coreSelector.getProperty(instanceType, fieldName);
    	if(property != null)
    	{
    		return property;
    	}
        return fieldSelector.getProperty(instanceType, fieldName);
    }

    @Override
    public <T> Collection<Property<T>> getSelectedFields( Class<T> instanceType )
    {
        Map<String, Property<T>> response = new TreeMap<String, Property<T>>();
        
        Collection<Property<T>> selectedFields = coreSelector.getSelectedFields( instanceType );
        for (Property<T> property : selectedFields)
        {
            response.put( property.name(), property );
        }

        Collection<Property<T>> fieldSelectorChildren = fieldSelector.getSelectedFields( instanceType );

        Collection<Property<T>> allMapFields = coreSelector.getAllPossibleFields( instanceType );

        for (Property<?> property : fieldSelectorChildren)
        {
            if (!response.containsKey( property.name() ))
            {
                for (Property<T> mapProp : allMapFields)
                {
                    if (mapProp.name().equals( property.name() ))
                    {
                        response.put( mapProp.name(), mapProp );
                    }
                }
            }
        }

        return response.values();
    }

    @Override
    public <T> Collection<Property<T>> getAllPossibleFields( Class<T> instanceType )
    {
        return coreSelector.getAllPossibleFields( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }
}
