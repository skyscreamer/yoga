package org.skyscreamer.yoga.springmvc.view;

import java.lang.reflect.Method;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * <p>
 * This is a bit of a hack to create ModelAndViewResolver as an adapter for a
 * ViewResolver, namely the ContentNegotiationViewResolver. There's plenty of
 * room for improvement here, especially since there isn't much logic in this
 * adapter. There's plenty of interesting additions that one can use to extend
 * or replace this class, including additional filtering logic.
 * </p>
 * 
 * <p>
 * You can log an issue or reach out to the development team about any
 * extensions you might be considering
 * </p>
 * 
 * @author solomon.duskis
 * 
 */
public class YogaModelAndViewResolverAdapter implements ModelAndViewResolver
{
    ViewResolver viewResolver;

    boolean requireResponseBodyAnnotation = false;

    public void setViewResolver(ViewResolver viewResolver)
    {
        this.viewResolver = viewResolver;
    }

    /** 
     * This is a failsafe to require the @ResponseBody annotation on the 
     * @param requireResponseBodyAnnotation
     */
    public void setRequireResponseBodyAnnotation(
            boolean requireResponseBodyAnnotation)
    {
        this.requireResponseBodyAnnotation = requireResponseBodyAnnotation;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ModelAndView resolveModelAndView(Method handlerMethod,
            Class handlerType, Object returnValue,
            ExtendedModelMap implicitModel, NativeWebRequest webRequest)
    {
        // Yoga doesn't handle nulls. Also, String values are likely to be view
        // names rather than results. The default behavior should be invoked,
        // and not the yoga logic.
        if(returnValue == null || returnValue instanceof String)
        {
            return ModelAndViewResolver.UNRESOLVED;
        }

        if(requireResponseBodyAnnotation && !handlerMethod.isAnnotationPresent(ResponseBody.class))
        {
            return ModelAndViewResolver.UNRESOLVED;
        }
        try
        {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            Locale locale = RequestContextUtils.getLocale(request);
            View view = viewResolver.resolveViewName("yoga", locale);
            return view == null ? ModelAndViewResolver.UNRESOLVED : new ModelAndView(view, "value", returnValue);
        } 
        catch (Exception e)
        {
            throw new RuntimeException("could not process request", e);
        }
    }

}
