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
    protected CompositeSelector createChildSelector(CoreSelector coreSelector,
            FieldSelector fieldSelector)
	{
	    return new CompositeStarSelector( coreSelector, fieldSelector );
	}
}
