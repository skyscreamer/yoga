package org.skyscreamer.yoga.springmvc.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.mapper.MapResultMapper;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.XhtmlHierarchyModel;
import org.skyscreamer.yoga.mapper.XmlHierarchyModel;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.util.NameUtil;
import org.springframework.beans.factory.annotation.Required;
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
   private MapResultMapper _resultMapper;
   private Map<String, String> _extensionMap;
   private ResultTraverser _resultTraverser;

   MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();

   @Required
   public void setResultMapper(MapResultMapper resultMapper)
   {
      this._resultMapper = resultMapper;
   }

   @Required
   public void setResultTraverser(ResultTraverser _resultTraverser)
   {
      this._resultTraverser = _resultTraverser;
   }

   @Required
   public void setExtensionMap(Map<String, String> extensionMap)
   {
      this._extensionMap = extensionMap;
   }

   public Map<String, String> getExtensionMap()
   {
      return _extensionMap;
   }

   @SuppressWarnings("rawtypes")
   public ModelAndView resolveModelAndView(Method handlerMethod, Class handlerType,
         Object returnValue, ExtendedModelMap implicitModel, NativeWebRequest webRequest)
   {
      if (AnnotationUtils.findAnnotation(handlerMethod, ResponseBody.class) != null)
      {
         return render(returnValue, webRequest);
      }
      return UNRESOLVED;
   }

   protected ModelAndView render(Object returnValue, NativeWebRequest webRequest)
   {
      final MediaType accept = getAcceptableAcccept(returnValue.getClass(), webRequest);

      if (accept == null)
      {
         return null;
      }

      return new ModelAndView(new View()
      {
         public void render(Map<String, ?> input, HttpServletRequest request,
               HttpServletResponse response) throws Exception
         {
            if (response.getContentType() == null)
               response.setContentType(accept.toString());

            String selectorString = request.getParameter("selector");
            Selector selector = SelectorParser.parseSelector(selectorString);
            Object value = input.values().iterator().next();

            // TODO: we need a more generic version of this that uses a
            // configuration map
            if (accept.getType().equals("application") && accept.getSubtype().equals("json"))
            {
               Object toRender = traverseJsonModel(value, selector);
               mappingJacksonHttpMessageConverter.write(toRender, accept,
                     new ServletServerHttpResponse(response));
            }
            else if (accept.getSubtype().equals("html"))
            {
               DOMDocument document = createXhtmlDocument(value, selector);
               write(response.getOutputStream(), document);
            }
            else if (accept.getSubtype().equals("xml"))
            {
               DOMDocument document = createXmlDocument(value, selector);
               write(response.getOutputStream(), document);
            }
         }

         public String getContentType()
         {
            return accept.toString();
         }
      }).addObject(returnValue);
   }

   protected MediaType getAcceptableAcccept(Class<?> returnType, NativeWebRequest nativeWebRequest)
   {
      HttpServletRequest req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
      String uri = req.getRequestURI();
      String extension = uri.replaceFirst(".*\\.([^.]+)", "$1");
      if (!extension.equals(uri) && _extensionMap.containsKey(extension))
      {
         return MediaType.valueOf(_extensionMap.get(extension));
      }
      ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(req);
      List<MediaType> accepts = servletServerHttpRequest.getHeaders().getAccept();
      for (MediaType mediaType : accepts)
      {
         if (mediaType.equals(MediaType.APPLICATION_XML) || mediaType.equals(MediaType.TEXT_HTML)
               || mediaType.equals(MediaType.APPLICATION_XHTML_XML))
         {
            return mediaType;
         }
         if (mappingJacksonHttpMessageConverter.canWrite(returnType, mediaType))
         {
            return mediaType;
         }
      }
      return null;
   }

   protected DOMDocument createXhtmlDocument(Object value, Selector selector)
   {
      DOMDocument domDocument = new DOMDocument();
      domDocument.setRootElement(new DOMElement("html"));
      Element body = domDocument.getRootElement().addElement("body");
      if (value instanceof Iterable)
      {
         for (Object child : (Iterable<?>) value)
         {
            traverseXhtml(child, selector, body);
         }
      }
      else
      {
         traverseXhtml(value, selector, body);
      }
      return domDocument;
   }

   protected void traverseXhtml(Object value, Selector selector, Element body)
   {
      String name = NameUtil.getName(_resultTraverser.getClass(value));
      HierarchicalModel model = new XhtmlHierarchyModel(body.addElement("div").addAttribute("class", name));
      _resultTraverser.traverse(value, selector, model);
   }

   protected DOMDocument createXmlDocument(Object value, Selector selector)
   {
      DOMDocument domDocument = new DOMDocument();
      if (value instanceof Iterable)
      {
         DOMElement root = new DOMElement("result");
         domDocument.setRootElement(root);
         HierarchicalModel model = new XmlHierarchyModel(root);
         for (Object child : (Iterable<?>) value)
         {
            _resultTraverser.traverse(child, selector, model);
         }
      }
      else
      {
         DOMElement root = new DOMElement(NameUtil.getName(_resultTraverser.getClass(value)));
         domDocument.setRootElement(root);
         HierarchicalModel model = new XmlHierarchyModel(root);
         _resultTraverser.traverse(value, selector, model);
      }
      return domDocument;
   }

   protected Object traverseJsonModel(Object returnValue, Selector selector)
   {
      if (returnValue instanceof Iterable<?>)
      {
         return _resultMapper.mapOutput((Iterable<?>) returnValue, selector);
      }
      else
      {
         return _resultMapper.mapOutput(returnValue, selector);
      }
   }

   protected void write(OutputStream output, DOMDocument domDocument) throws IOException
   {
      OutputStreamWriter out = new OutputStreamWriter(output);
      domDocument.write(out);
      out.flush();
   }

}
