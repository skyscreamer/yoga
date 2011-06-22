package org.skyscreamer.yoga.springmvc.view;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.controller.ControllerResponse;
import org.skyscreamer.yoga.mapper.ResultMapper;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
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

public class SelectorModelAndViewResolver implements ModelAndViewResolver
{
    private ResultMapper _resultMapper;

    MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();

    public void setResultMapper(ResultMapper resultMapper) 
    {
		this._resultMapper = resultMapper;
	}
    
    @SuppressWarnings("rawtypes")
    public ModelAndView resolveModelAndView( Method handlerMethod, Class handlerType, Object returnValue,
        ExtendedModelMap implicitModel, NativeWebRequest webRequest )
    {
        if ( returnValue instanceof ControllerResponse )
        {
            ControllerResponse controllerResponse = (ControllerResponse)returnValue;
            if ( controllerResponse.getData() != null &&
                AnnotationUtils.findAnnotation( handlerMethod, ResponseBody.class ) != null )
            {
                return render( controllerResponse, webRequest );
            }
        }
        return UNRESOLVED;
    }

    protected ModelAndView render( final ControllerResponse controllerResponse, NativeWebRequest webRequest )
    {
        final MediaType accept = getAcceptableAcccept( controllerResponse.getData().getClass(), webRequest );

        if ( accept == null )
        {
            return null;
        }

        return new ModelAndView( new View()
        {
            public void render( Map<String, ?> input, HttpServletRequest request, HttpServletResponse response )
                    throws Exception
            {
                String selectorString = request.getParameter( "selector" );
                Object toRender = getModel( input.values().iterator().next(),
                        SelectorParser.parseSelector( selectorString ) );
                mappingJacksonHttpMessageConverter.write( toRender, accept, new ServletServerHttpResponse( response ) );
            }

            public String getContentType()
            {
                return accept.toString();
            }
        } ).addObject( controllerResponse.getData() );
    }

    protected Object getModel( Object returnValue, Selector selector )
    {
        if ( returnValue instanceof Collection<?> )
        {
            return _resultMapper.populate( (Collection<?>) returnValue, selector );
        }
        else
        {
            return _resultMapper.populate( returnValue, selector );
        }
    }

    protected MediaType getAcceptableAcccept( Class<?> returnType, NativeWebRequest nativeWebRequest )
    {
        HttpServletRequest req = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest( req );
        List<MediaType> accepts = servletServerHttpRequest.getHeaders().getAccept();
        for ( MediaType mediaType : accepts )
        {
            if ( mappingJacksonHttpMessageConverter.canWrite( returnType, mediaType ) )
            {
                return mediaType;
            }
        }
        return null;
    }
}
