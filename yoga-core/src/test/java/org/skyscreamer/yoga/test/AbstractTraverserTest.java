package org.skyscreamer.yoga.test;

import org.junit.Assert;
import org.skyscreamer.yoga.enricher.Enricher;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaInstanceContextFactory;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.MapHierarchicalModel;
import org.skyscreamer.yoga.populator.DefaultFieldPopulatorRegistry;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.AliasSelectorResolver;
import org.skyscreamer.yoga.selector.LinkedInSelectorParser;
import org.skyscreamer.yoga.selector.ParseSelectorException;
import org.skyscreamer.yoga.selector.SelectorParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: corby
 * Date: 5/7/12
 */
public abstract class AbstractTraverserTest
{
    protected YogaRequestContext _simpleContext = new YogaRequestContext( "map", new DummyHttpServletResponse() );
    protected MapHierarchicalModel _model = new MapHierarchicalModel();
    protected Map<String, Object> _objectTree;

    protected Class<? extends SelectorParser> _selectorParserClass = LinkedInSelectorParser.class;
    protected AliasSelectorResolver _aliasSelectorResolver;

    protected ResultTraverser traverserNoEnrichers()
    {
        return traverserWithEnrichers();
    }

    protected ResultTraverser traverserWithEnrichers( Enricher... enrichers )
    {
        ResultTraverser result = new ResultTraverser();
        result.setEnrichers( Arrays.asList( enrichers ) );
        result.setInstanceContextFactory( new YogaInstanceContextFactory() );
        return result;
    }

    protected void doTraverse( Object instance, String selectorString, ResultTraverser traverser, YogaRequestContext context )
    {
        try
        {
            SelectorParser selectorParser = _selectorParserClass.newInstance();
            selectorParser.setAliasSelectorResolver( _aliasSelectorResolver );

            traverser.traverse( instance, selectorParser.parseSelector( selectorString ), _model, context );
            _objectTree = _model.getObjectTree();
        }
        catch ( ParseSelectorException e )
        {
            Assert.fail( "Could not parse selector string " + selectorString );
        }
        catch ( InstantiationException e )
        {
            Assert.fail( "Could not instantiate " + _selectorParserClass );
        }
        catch ( IllegalAccessException e )
        {
            Assert.fail( "Could not instantiate " + _selectorParserClass );
        }
    }

    protected void addFieldPopulators( ResultTraverser traverser, FieldPopulator... populators )
    {
        YogaInstanceContextFactory contextFactory = new YogaInstanceContextFactory();
        contextFactory.setFieldPopulatorRegistry( new DefaultFieldPopulatorRegistry( Arrays.asList( populators ) ) );
        traverser.setInstanceContextFactory( contextFactory );
    }

    @SuppressWarnings( "unchecked" )
    protected List<Map<String, Object>> getList( Map<String,Object> map, String s )
    {
        return (List<Map<String, Object>>) map.get( s );
    }

    protected Map<String, Object> findItem( List<Map<String, Object>> list, String key, Object value )
    {
        for ( Map<String, Object> item : list )
        {
            if ( item.get( key ) != null && item.get( key ).equals( value ) )
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
