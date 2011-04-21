package org.skyscreamer.yoga.converter;

import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.core.convert.converter.Converter;

public class SelectorConverter implements Converter<String, Selector>
{
    public Selector convert( String selectorExpression )
    {
        try
        {
            return new Selector( selectorExpression );
        }
        catch ( ParseSelectorException e )
        {
            // TODO: Add logging here.  Spring spits out "no matching editors or conversion strategy found", which is
            // vague and misleading.  (ie, A URL typo looks like a configuration error)
            throw new IllegalArgumentException( "Could not parse selector", e );
        }
    }
}
