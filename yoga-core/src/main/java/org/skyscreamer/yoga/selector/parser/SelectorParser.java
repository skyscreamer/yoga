package org.skyscreamer.yoga.selector.parser;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.CompositeSelector;
import org.skyscreamer.yoga.selector.FieldSelector;
import org.skyscreamer.yoga.selector.Selector;

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

    public Selector parseSelector( String selectorExpression ) throws ParseSelectorException
    {
        CompositeSelector parent = new CompositeSelector();
        parseSelector( selectorExpression, parent );
        return parent;
    }

    public void parseSelector( String selectorExpression, CompositeSelector parent ) throws ParseSelectorException
    {
        if ( selectorExpression == null )
        {
            return;
        }

        if ( _disableExplicitSelectors && !selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            throw new ParseSelectorException( "Explicit selectors have been disabled" );
        }

        if ( selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            selectorExpression = _aliasSelectorResolver.resolveSelector( selectorExpression );
        }

        if ( selectorExpression != null )
        {
            FieldSelector fieldSelector = parse( selectorExpression );
            if(fieldSelector != null && !fieldSelector.getSelectors( null ).isEmpty() )
            {
                parent.add( fieldSelector );
            }
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
}

