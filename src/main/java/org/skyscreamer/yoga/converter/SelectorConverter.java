package org.skyscreamer.yoga.converter;

import org.skyscreamer.yoga.selector.Selector;
import org.springframework.core.convert.converter.Converter;

public class SelectorConverter implements Converter<String, Selector>
{
    public Selector convert( String selectorExpression )
    {
        return new Selector( selectorExpression );
    }
}
