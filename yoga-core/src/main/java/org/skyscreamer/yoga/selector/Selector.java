package org.skyscreamer.yoga.selector;

import java.util.Map;
import java.util.Set;

public interface Selector
{

    boolean containsField( Class<?> instanceType, String property );

    Set<String> getSelectedFieldNames( Class<?> instanceType );

    Set<String> getAllPossibleFields( Class<?> instanceType );

    boolean isInfluencedExternally();

    Selector getChildSelector( Class<?> instanceType, String fieldName );

    Map<String, Selector> getSelectors( Class<?> instanceType );

}
