package org.skyscreamer.yoga.test.util;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.populator.FieldPopulatorRegistry;
import org.skyscreamer.yoga.selector.CompositeSelector;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.parser.AliasSelectorResolver;
import org.skyscreamer.yoga.selector.parser.LinkedInSelectorParser;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

/**
 * User: corby Date: 5/7/12
 */
public abstract class AbstractTraverserTest
{
    protected YogaRequestContext _simpleContext = new YogaRequestContext( "map",
            new DummyHttpServletRequest(), new DummyHttpServletResponse() );

    protected Class<? extends SelectorParser> _selectorParserClass = LinkedInSelectorParser.class;
    protected AliasSelectorResolver _aliasSelectorResolver;

    protected Map<String, Object> doTraverse( Object instance, String selectorString, ResultTraverser traverser,
            YogaRequestContext context )
    {
        try
        {
            Constructor<? extends SelectorParser> constructor = _selectorParserClass.getConstructor( FieldPopulatorRegistry.class );
            SelectorParser selectorParser = constructor.newInstance( traverser.getFieldPopulatorRegistry() );
            selectorParser.setAliasSelectorResolver( _aliasSelectorResolver );
            CompositeSelector selector = new CompositeSelector( new CoreSelector( traverser.getFieldPopulatorRegistry() ) );
            selectorParser.parseSelector( selectorString, selector );

            return doTraverse( instance, traverser, context, selector );
        }
        catch (ParseSelectorException e)
        {
            Assert.fail( "Could not parse selector string " + selectorString );
        }
        catch (Exception e)
        {
            Assert.fail( "Could not instantiate " + _selectorParserClass );
        }
        return null;
    }

    private Map<String, Object> doTraverse( Object instance, ResultTraverser traverser, YogaRequestContext context,
            Selector selector )
    {
        ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
        traverser.traverse( instance, selector, model, context );
        return model.getUnderlyingModel();
    }

    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> getList( Map<String, Object> map, String s )
    {
        return (List<Map<String, Object>>) map.get( s );
    }

    protected Map<String, Object> findItem( List<Map<String, Object>> list, String key, Object value )
    {
        for (Map<String, Object> item : list)
        {
            if (item.get( key ) != null && item.get( key ).equals( value ))
            {
                return item;
            }
        }
        return null;
    }

    public void setSelectorParserClass( Class<? extends SelectorParser> selectorParserClass )
    {
        _selectorParserClass = selectorParserClass;
    }

    public void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver )
    {
        _aliasSelectorResolver = aliasSelectorResolver;
    }
}
