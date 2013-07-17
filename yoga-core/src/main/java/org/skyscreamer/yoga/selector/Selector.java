package org.skyscreamer.yoga.selector;

import java.util.Collection;

public interface Selector
{

    Collection<Property> getSelectedFields( Class<?> instanceType );

    Collection<Property> getAllPossibleFields( Class<?> instanceType );

    boolean containsField( Class<?> instanceType, String property );

    boolean isInfluencedExternally();

    Selector getChildSelector( Class<?> instanceType, String fieldName );
}
