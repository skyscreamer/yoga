package org.skyscreamer.yoga.selector;

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

    protected abstract Selector parse( String selectorExpression ) throws ParseSelectorException;

    public Selector parseSelector( String selectorExpression ) throws ParseSelectorException
    {
        if ( selectorExpression == null )
        {
            return new CoreSelector();
        }

        if ( _disableExplicitSelectors && !selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            throw new ParseSelectorException( "Explicit selectors have been disabled" );
        }

        if ( selectorExpression.startsWith( ALIAS_SELECTOR_PREFIX ) )
        {
            selectorExpression = _aliasSelectorResolver.resolveSelector( selectorExpression );
        }

        Selector selector = new CoreSelector();
        if ( selectorExpression != null )
        {
            selector = new CombinedSelector( selector, parse( selectorExpression ) );
        }
        return selector;
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

