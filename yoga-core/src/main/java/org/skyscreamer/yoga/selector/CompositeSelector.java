package org.skyscreamer.yoga.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CompositeSelector implements Selector
{
    protected final CoreSelector coreSelector;
    protected final FieldSelector fieldSelector;

    public CompositeSelector( CoreSelector coreSelector, FieldSelector fieldSelector )
    {
        this.coreSelector = coreSelector;
        this.fieldSelector = fieldSelector;
    }

    @Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        FieldSelector fieldSelectorChild = fieldSelector.getChildSelector( instanceType, fieldName );
        if (fieldSelectorChild == null)
        {
            return coreSelector;
        }
        else
        {
            return new CompositeSelector( coreSelector, fieldSelectorChild );
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
        return property != null ? property : fieldSelector.getProperty(instanceType, fieldName);
    }

    @Override
    public <T> Collection<Property<T>> getSelectedFields( Class<T> instanceType )
    {
        ArrayList<Property<T>> list = new ArrayList<Property<T>>();
        for( Property<T> p : fieldSelector.getSelectedFields( instanceType ) )
        {
            Property<T> property = coreSelector.getProperty( instanceType, p.name() );
            if(property != null)
            {
                list.add( property );
            }
        }
        Collection<Property<T>> coreFields = coreSelector.getSelectedFields( instanceType );
        if ( coreFields != null )
        {
            list.addAll( coreFields );
        }
        return list;
    }

    @Override
    public <T> Map<String, Property<T>> getAllPossibleFieldMap( Class<T> instanceType )
    {
        return coreSelector.getAllPossibleFieldMap( instanceType );
    }

    @Override
    public boolean isInfluencedExternally()
    {
        return true;
    }
}
