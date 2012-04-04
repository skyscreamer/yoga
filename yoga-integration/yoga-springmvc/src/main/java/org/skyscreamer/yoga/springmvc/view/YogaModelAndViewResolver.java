package org.skyscreamer.yoga.springmvc.view;

import java.lang.reflect.Method;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class YogaModelAndViewResolver implements ModelAndViewResolver {
	ViewResolver viewResolver;

	public void setViewResolver(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ModelAndView resolveModelAndView(Method handlerMethod, Class handlerType, Object returnValue, ExtendedModelMap implicitModel, NativeWebRequest webRequest) {
		try {
			HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
			Locale locale = RequestContextUtils.getLocale(nativeRequest);
			View view = viewResolver.resolveViewName("yoga", locale);
			return new ModelAndView(view, "value", returnValue);
		} catch (Exception e) {
			throw new RuntimeException("could not process request", e);
		}
	}

}
