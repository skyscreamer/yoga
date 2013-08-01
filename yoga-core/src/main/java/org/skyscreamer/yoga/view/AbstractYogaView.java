package org.skyscreamer.yoga.view;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.util.NameUtil;

/**
 * This class represents the entry point to yoga. It integrates with view
 * technologies such as Spring Views and JAX-RS MessageBodyWriters. This objects
 * sets up the yoga related context information (listeners, the selector, the
 * suffix) and passes it along to the children to render.
 * 
 * @see org.skyscreamer.yoga.view.JsonSelectorView
 * @see org.skyscreamer.yoga.view.XmlSelectorView
 * @see org.skyscreamer.yoga.view.XhtmlSelectorView
 * 
 * @author Solomon Duskis
 */
public abstract class AbstractYogaView
{
    protected ResultTraverser _resultTraverser = new ResultTraverser();

    protected SelectorResolver _selectorResolver = new SelectorResolver();

    protected RenderingListenerRegistry _registry = new RenderingListenerRegistry();

    public void setResultTraverser( ResultTraverser resultTraverser )
    {
        this._resultTraverser = resultTraverser;
    }

    @Deprecated
    /** use setSelectorResolver instead */
    public void setSelectorParser( SelectorParser selectorParser )
    {
        this._selectorResolver.setSelectorParser( selectorParser );
    }

    @Deprecated
    /** use setSelectorResolver instead */
    public void setSelector( CoreSelector selector )
    {
        this._selectorResolver.setBaseSelector( selector );
    }


    public void setRegistry( RenderingListenerRegistry registry )
    {
        this._registry = registry;
    }

    public void setSelectorResolver( SelectorResolver selectorResolver )
    {
        this._selectorResolver = selectorResolver;
    }

    public SelectorResolver getSelectorResolver()
    {
        return _selectorResolver;
    }

    public ResultTraverser getResultTraverser()
    {
        return _resultTraverser;
    }

    public ClassFinderStrategy getClassFinderStrategy()
    {
        return _resultTraverser.getClassFinderStrategy();
    }
    
    public void setClassFinderStrategy( ClassFinderStrategy classFinderStrategy )
    {
        _resultTraverser.setClassFinderStrategy( classFinderStrategy );
    }

    public RenderingListenerRegistry getRegistry()
    {
        return _registry;
    }

    public final void render( HttpServletRequest request, HttpServletResponse response, Object value, OutputStream os )
            throws Exception
    {
        YogaRequestContext context = new YogaRequestContext( getHrefSuffix(), getSelectorResolver(), request, response,
                _registry.getListeners() );
        render( value, context, os );
    }

    protected String getClassName( Object obj )
    {
        Class<?> type = getClassFinderStrategy().findClass( obj );
        return NameUtil.getName( type );
    }

    public abstract String getContentType();

    protected abstract void render( Object value, YogaRequestContext context, OutputStream os ) throws Exception;

    public abstract String getHrefSuffix();
}
