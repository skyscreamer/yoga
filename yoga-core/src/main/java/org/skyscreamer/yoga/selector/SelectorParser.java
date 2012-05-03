package org.skyscreamer.yoga.selector;

/**
 * A SelectorParser takes a string selector argument and translates it into a Selector which is used to navigate
 * the object tree and build results.
 *
 * @author Solomon Duskis <solomon@skyscreamer.org>
 * @author Corby Page <corby@skyscreamer.org>
 * @author Carter Page <carter@skyscreamer.org>
 */
public abstract class SelectorParser
{
    public static final String HREF = "href";
    public static final String DEFINITION = "definition";

    public abstract Selector parseSelector( String selectorStr );
    public abstract Selector parse( String selectorExpression ) throws ParseSelectorException;
    public abstract void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver );
    public abstract void setDisableExplicitSelectors( boolean disableExplicitSelectors );
}
