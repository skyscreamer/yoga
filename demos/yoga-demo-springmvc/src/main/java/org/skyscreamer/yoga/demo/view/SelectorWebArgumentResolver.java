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
            String selectorStr = webRequest.getParameter( "selector" );
            return SelectorParser.parseSelector( selectorStr );
        }

        return UNRESOLVED;
    }
}
