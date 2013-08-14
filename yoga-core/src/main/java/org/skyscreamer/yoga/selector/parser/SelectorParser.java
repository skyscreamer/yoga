package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.FieldSelector;

/**
 * A SelectorParser takes a string selector argument and translates it into a
 * Selector which is used to navigate the object tree and build results.
 *
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 */
public abstract class SelectorParser
{
    public static final String ALIAS_SELECTOR_PREFIX = "$";
    public static final String HREF = "href";
    public static final String DEFINITION = "definition";

    protected AliasSelectorResolver _aliasSelectorResolver;
    protected boolean _disableExplicitSelectors = false;

    protected abstract FieldSelector parse( String selectorExpression ) throws ParseSelectorException;

    public abstract String getSelectorJavascriptURL();
    public abstract Object getSelectorType();

    public FieldSelector parseSelector( String selectorExpression ) throws ParseSelectorException
    {
        if ( selectorExpression == null )
        {
            return null;
        }

        if ( _disableExplicitSelectors && !selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            throw new ParseSelectorException( "Explicit selectors have been disabled" );
        }

        if ( selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            selectorExpression = _aliasSelectorResolver.resolveSelector( selectorExpression );
        }

        FieldSelector fieldSelector = parse( selectorExpression );

        if(fieldSelector != null && !fieldSelector.getAllPossibleFieldMap( null ).isEmpty() )
        {
            return fieldSelector;
        }
        else
        {
            return null;
        }
    }

    public void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver )
    {
        _aliasSelectorResolver = aliasSelectorResolver;
    }

    public void setDisableExplicitSelectors( boolean disableExplicitSelectors )
    {
        _disableExplicitSelectors = disableExplicitSelectors;
    }

    @Deprecated
    /** this method wasn't all that useful.  This registry really only helped the Core selector, not the url parsed fields */
    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry)
    {
    }

}

