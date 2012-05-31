package org.skyscreamer.yoga.selector;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class CompositeSelector implements Selector
{

    private MapSelector mapSelector;
    private FieldSelector fieldSelector;

    public CompositeSelector( MapSelector mapSelector, FieldSelector fieldSelector )
    {
        this.mapSelector = mapSelector;
        this.fieldSelector = fieldSelector;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        FieldSelector fieldSelectorChild = (FieldSelector) fieldSelector.getChildSelector(
                instanceType, fieldName );
        MapSelector mapSelectorChild = (MapSelector) mapSelector.getChildSelector( instanceType,
                fieldName );
        if (fieldSelectorChild == null)
        {
            return mapSelectorChild;
        }
        else
        {
            return new CompositeSelector( mapSelectorChild, fieldSelectorChild );
        }
    }

    @Override
    public boolean containsField( Class<?> instanceType, String property )
    {
        return mapSelector.containsField( instanceType, property )
                || fieldSelector.containsField( instanceType, property );
    }

    @Override
    public Collection<Property> getSelectedFields( Class<?> instanceType, Object instance )
    {
        Map<String, Property> response = new TreeMap<String, Property>();
        
        Collection<Property> selectedFields = mapSelector.getSelectedFields( instanceType, instance );
        for (Property property : selectedFields)
        {
            response.put( property.name(), property );
        }

        Collection<Property> fieldSelectorChildren = fieldSelector.getSelectedFields( instanceType, instance );

        Collection<Property> allMapFields = mapSelector.getAllPossibleFields( instanceType );

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
        return mapSelector.getAllPossibleFields( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }
}
