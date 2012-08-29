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
    public Collection<Property> getSelectedFields( Class<?> instanceType, Object instance )
    {
        Map<String, Property> response = new TreeMap<String, Property>();
        
        Collection<Property> selectedFields = coreSelector.getSelectedFields( instanceType, instance );
        for (Property property : selectedFields)
        {
            response.put( property.name(), property );
        }

        Collection<Property> fieldSelectorChildren = fieldSelector.getSelectedFields( instanceType, instance );

        Collection<Property> allMapFields = coreSelector.getAllPossibleFields( instanceType );

        for (Property property : fieldSelectorChildren)
        {
            if (!response.containsKey( property.name() ))
            {
                for (Property mapProp : allMapFields)
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
    public Collection<Property> getAllPossibleFields( Class<?> instanceType )
    {
        return coreSelector.getAllPossibleFields( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }
}
