package org.skyscreamer.yoga.demo.view;

import org.skyscreamer.yoga.selector.*;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public class SelectorWebArgumentResolver implements WebArgumentResolver
{
    public Object resolveArgument( MethodParameter methodParameter, NativeWebRequest webRequest ) throws Exception
    {
        if ( Selector.class == methodParameter.getParameterType() )
        {
            CoreSelector coreSelector = new CoreSelector();
            String selectorStr = webRequest.getParameter( "selector" );
            if ( selectorStr != null )
            {
                try
                {
                    return new CombinedSelector( coreSelector, SelectorParser.parse( selectorStr ) );
                }
                catch ( ParseSelectorException e )
                {
                    // TODO: Add logging here. Spring spits out "no matching editors or conversion strategy found",
                    // which is vague and misleading. (ie, A URL typo looks like a configuration error)
                    throw new IllegalArgumentException( "Could not parse selector", e );
                }
            }
            return coreSelector;
        }

        return UNRESOLVED;
    }
}
