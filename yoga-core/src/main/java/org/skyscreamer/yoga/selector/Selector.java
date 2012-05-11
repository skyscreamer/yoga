package org.skyscreamer.yoga.selector;

import java.util.Map;
import java.util.Set;

public interface Selector
{
    Selector getSelector( Class<?> instanceType, String fieldName );

    boolean containsField( Class<?> instanceType, String property );

    Set<String> getSelectedFieldNames( Class<?> instanceType );

    Map<String, Selector> getSelectors( Class<?> instanceType );
    
    Set<String> getAllPossibleFields( Class<?> instanceType );
}
