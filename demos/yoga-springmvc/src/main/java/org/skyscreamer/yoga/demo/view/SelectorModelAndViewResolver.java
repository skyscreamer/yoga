package org.skyscreamer.yoga.demo.view;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.populator.ObjectFieldPopulator;
import org.skyscreamer.yoga.selector.CombinedSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

public class SelectorModelAndViewResolver implements ModelAndViewResolver {

	@Autowired
	ObjectFieldPopulator _objectFieldPopulator;

	MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();

	@SuppressWarnings("rawtypes")
	@Override
	public ModelAndView resolveModelAndView(Method handlerMethod, Class handlerType, Object returnValue, ExtendedModelMap implicitModel, NativeWebRequest webRequest) {
		if (returnValue == null || AnnotationUtils.findAnnotation(handlerMethod, ResponseBody.class) == null) {
			return null;
		}
		return render(returnValue, webRequest);
	}

	protected ModelAndView render(final Object returnValue, NativeWebRequest webRequest) {
		HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
		final MediaType accept = getAcceptableAcccept(returnValue.getClass(), req);
		
		if (accept == null) {
			return null;
		}
		
		return new ModelAndView(new View() {
			@Override
			public void render(Map<String, ?> input, HttpServletRequest request, HttpServletResponse response) throws Exception {
				Object toRender = getModel(input.values().iterator().next(), request);
				mappingJacksonHttpMessageConverter.write(toRender, accept,  new ServletServerHttpResponse(response));
			}
			
			@Override
			public String getContentType() {
				return accept.toString();
			}
		}).addObject(returnValue);
	}

	protected Object getModel(Object returnValue, HttpServletRequest req) {
		if (returnValue instanceof Collection<?>) {
			return _objectFieldPopulator.populate((Collection<?>) returnValue, getSelector(req));
		} else {
			return _objectFieldPopulator.populate(returnValue, getSelector(req));
		}
	}

	protected Selector getSelector(HttpServletRequest req) {
		CoreSelector coreSelector = new CoreSelector();
		String selectorStr = req.getParameter("selector");
		if (selectorStr != null) {
			try {
				return new CombinedSelector(coreSelector, SelectorParser.parse(selectorStr));
			} catch (ParseSelectorException e) {
				// TODO: Add logging here. Spring spits out
				// "no matching editors or conversion strategy found", which
				// is vague and misleading. (ie, A URL typo looks like a
				// configuration error)
				throw new IllegalArgumentException("Could not parse selector", e);
			}
		}
		return coreSelector;
	}

	protected MediaType getAcceptableAcccept(Class<?> returnType, HttpServletRequest req) {
		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(req);
		List<MediaType> accepts = servletServerHttpRequest.getHeaders().getAccept();
		for (MediaType mediaType : accepts) {
			if (mappingJacksonHttpMessageConverter.canWrite(returnType, mediaType)) {
				return mediaType;
			}
		}
		return null;
	}
}
