package org.skyscreamer.yoga.springmvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.CompositeSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;

/**
 * This MessageConvert gets the selector from the request. Children do the
 * interesting output. NOTE: you have to put in a
 * org.skyscreamer.yoga.springmvc.view.RequestHolder in your web.xml file
 * 
 * @author Solomon Duskis
 */
public abstract class AbstractYogaView implements View
{
    @Autowired
    protected ResultTraverser resultTraverser;

    @Autowired
    protected SelectorParser selectorParser;

    @Autowired
    protected RenderingListenerRegistry registry;

    public void setResultTraverser( ResultTraverser resultTraverser )
    {
        this.resultTraverser = resultTraverser;
    }

    public void setSelectorParser( SelectorParser selectorParser )
    {
        this.selectorParser = selectorParser;
    }

    public void setRegistry( RenderingListenerRegistry registry )
    {
        this.registry = registry;
    }

    @Override
    public void render( Map<String, ?> model, HttpServletRequest request,
            HttpServletResponse response ) throws Exception
    {
        response.setContentType( getContentType() );
        YogaRequestContext context = new YogaRequestContext( getHrefSuffix(), request, response,
                registry.getListeners() );
        Object value = model.values().iterator().next();
        Selector selector = getSelector( request );
        render( selector, value, context );
    }

    public Selector getSelector( HttpServletRequest request ) throws ParseSelectorException
    {
        String selectorString = request.getParameter( "selector" );
        CoreSelector coreSelector = new CoreSelector();
        CompositeSelector composite = new CompositeSelector( coreSelector );
        selectorParser.parseSelector( selectorString, composite );
        return composite;
    }

    public abstract void render( Selector selector, Object value, YogaRequestContext context )
            throws Exception;

    public abstract String getHrefSuffix();
}
