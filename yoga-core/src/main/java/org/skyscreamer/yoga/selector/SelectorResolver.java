package org.skyscreamer.yoga.selector;

import javax.servlet.http.HttpServletRequest;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class SelectorResolver
{

    protected SelectorParser _selectorParser;
    protected MapSelector _baseSelector = new CoreSelector();
    protected String _selectorParameterName = "selector";
    
    public SelectorResolver()
    {
    }

    public SelectorResolver(SelectorParser selectorParser, MapSelector baseSelector)
    {
        super();
        this._selectorParser = selectorParser;
        this._baseSelector = baseSelector;
    }

    public SelectorParser getSelectorParser()
    {
        return _selectorParser;
    }

    public void setSelectorParser( SelectorParser selectorParser )
    {
        this._selectorParser = selectorParser;
    }

    public MapSelector getBaseSelector()
    {
        return _baseSelector;
    }

    public void setBaseSelector( MapSelector baseSelector )
    {
        this._baseSelector = baseSelector;
    }
    
    

    public String getSelectorParameterName()
    {
        return _selectorParameterName;
    }

    public void setSelectorParameterName(String selectorParameterName)
    {
        this._selectorParameterName = selectorParameterName;
    }

    public Selector getSelector( HttpServletRequest request )
            throws ParseSelectorException
    {
        String selectorString = request.getParameter(_selectorParameterName);
        return _selectorParser.parseSelector(selectorString, _baseSelector);
    }
}
