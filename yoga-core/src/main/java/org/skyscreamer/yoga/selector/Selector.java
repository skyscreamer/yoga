package org.skyscreamer.yoga.selector;

import java.util.Collection;
import java.util.Map;

public interface Selector
{

    <T> Collection<Property<T>> getSelectedFields( Class<T> instanceType );

    <T> Map<String, Property<T>> getAllPossibleFieldMap( Class<T> instanceType );

	boolean containsField( Class<?> instanceType, String fieldName );

    boolean isInfluencedExternally();

    Selector getChildSelector( Class<?> instanceType, String fieldName );

    <T> Property<T> getProperty( Class<T> instanceType, String fieldName );

}
