package org.skyscreamer.yoga.selector;

import java.util.Collection;

public class CompositeStarSelector extends CompositeSelector
{
	public CompositeStarSelector( CoreSelector coreSelector, FieldSelector fieldSelector )
	{
		super( coreSelector, fieldSelector );
	}

	@Override
	public <T> Collection<Property<T>> getSelectedFields(Class<T> instanceType)
	{
	    if (fieldSelector.containsField( instanceType, "*" ) ) 
	    {
	        return coreSelector.getAllPossibleFieldMap( instanceType ).values();
	    }
	    else
	    {
	        return super.getSelectedFields( instanceType );
	    }
	}

	@Override
    public Selector getChildSelector( Class<?> instanceType, String fieldName )
    {
        FieldSelector childSelector = fieldSelector.getChildSelector( instanceType, fieldName );
	    if ( childSelector == null && fieldSelector.containsField( "*" ) ) 
	    {
	        childSelector = fieldSelector.getChildSelector( instanceType, "*" );
	    }
        return getChildSelector( childSelector );
    }

    private Selector getChildSelector(FieldSelector fieldSelectorChild)
    {
        if ( fieldSelectorChild == null )
        {
            return coreSelector;
        }
        else
        {
            return new CompositeStarSelector( coreSelector, fieldSelectorChild );
        }
    }
}
