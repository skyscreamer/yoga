package org.skyscreamer.yoga.view;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.MapSelector;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.NameUtil;

/**
 * This MessageConvert gets the selector from the request. Children do the
 * interesting output. 
 * 
 * @author Solomon Duskis
 */
public abstract class AbstractYogaView
{
    protected ResultTraverser _resultTraverser = new ResultTraverser();

    protected ClassFinderStrategy _classFinderStrategy;

    protected SelectorParser _selectorParser;

    protected RenderingListenerRegistry _registry;

    protected MapSelector _selector;

    public void setResultTraverser( ResultTraverser resultTraverser )
    {
        this._resultTraverser = resultTraverser;
    }

    public void setSelectorParser( SelectorParser selectorParser )
    {
        this._selectorParser = selectorParser;
    }

    public void setRegistry( RenderingListenerRegistry registry )
    {
        this._registry = registry;
    }

    public void setSelector( MapSelector selector )
    {
        this._selector = selector;
    }

    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        this._classFinderStrategy = classFinderStrategy;
        _resultTraverser.setClassFinderStrategy( classFinderStrategy );
    }

    public final void render( HttpServletRequest request, HttpServletResponse response, Object value, OutputStream os )
            throws Exception
    {
        YogaRequestContext context = new YogaRequestContext( getHrefSuffix(), request, response, _registry.getListeners() );
        Selector selector = getSelector( request );
        render1( selector, value, context, os );
    }

    public Selector getSelector( HttpServletRequest request ) throws ParseSelectorException
    {
        String selectorString = request.getParameter( "selector" );
        return _selectorParser.parseSelector( selectorString, _selector );
    }

    protected String getClassName(Object obj)
    {
        Class<?> type = _classFinderStrategy.findClass( obj );
        return NameUtil.getName( type );
    }

    public abstract String getContentType();

    protected abstract void render1( Selector selector, Object value, YogaRequestContext context, OutputStream os )
            throws Exception;

    public abstract String getHrefSuffix();
}
