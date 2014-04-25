package org.skyscreamer.yoga.selector;

import javax.servlet.http.HttpServletRequest;

import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

public class SelectorResolver
{

    protected SelectorParser _selectorParser;
    protected CoreSelector _baseSelector;
    protected String _selectorParameterName = "selector";
    protected boolean _starResolvesToAll = false;

    public SelectorResolver()
    {
        this( new GDataSelectorParser() );
    }

    public SelectorResolver( SelectorParser selectorParser )
    {
        this( selectorParser, new CoreSelector() );
    }

    public SelectorResolver( SelectorParser selectorParser, CoreSelector baseSelector )
    {
        this._selectorParser = selectorParser;
        this._baseSelector = baseSelector;
    }

    public SelectorParser getSelectorParser()
    {
        return _selectorParser;
    }

    public void setStarResolvesToAll(boolean _starResolvesToAll)
    {
        this._starResolvesToAll = _starResolvesToAll;
    }
    public void setSelectorParser( SelectorParser selectorParser )
    {
        this._selectorParser = selectorParser;
    }

    public CoreSelector getBaseSelector()
    {
        return _baseSelector;
    }

    public void setBaseSelector( CoreSelector baseSelector )
    {
        this._baseSelector = baseSelector;
    }

    public String getSelectorParameterName()
    {
        return _selectorParameterName;
    }

    public void setSelectorParameterName( String selectorParameterName )
    {
        this._selectorParameterName = selectorParameterName;
    }

    public Selector getSelector( HttpServletRequest request )
            throws ParseSelectorException
    {
        String selectorString = request.getParameter( _selectorParameterName );
        return resolveSelector( selectorString );
    }
    
    public void setEntityConfigurationRegistry( EntityConfigurationRegistry entityConfigurationRegistry)
    {
        this._baseSelector.setEntityConfigurationRegistry( entityConfigurationRegistry );
    }

    public Selector resolveSelector( String selectorExpression ) throws ParseSelectorException
    {
        FieldSelector fieldSelector = _selectorParser.parseSelector( selectorExpression );

        if (fieldSelector != null)
        {
            if ( _starResolvesToAll ) 
            {
                return new CompositeStarSelector( _baseSelector, fieldSelector );
            }
            else 
            {
                return new CompositeSelector( _baseSelector, fieldSelector );
            }
        }
        else
        {
            return _baseSelector;
        }
    }
}
