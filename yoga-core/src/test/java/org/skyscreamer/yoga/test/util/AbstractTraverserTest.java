package org.skyscreamer.yoga.test.util;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.EntityConfigurationRegistry;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.listener.RenderingListener;
import org.skyscreamer.yoga.mapper.ResultTraverser;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.selector.parser.AliasSelectorResolver;

/**
 * User: corby Date: 5/7/12
 */
public abstract class AbstractTraverserTest
{
    private final SelectorResolver resolver  = new SelectorResolver();

    {
        resolver.setEntityConfigurationRegistry( new DefaultEntityConfigurationRegistry() );
    }

    protected Map<String, Object> doTraverse( Object instance, String selectorString, ResultTraverser traverser, RenderingListener ... listeners )
    {
        try
        {
            YogaRequestContext context = new YogaRequestContext( "test", resolver,
                    new DummyHttpServletRequest(), new DummyHttpServletResponse(), listeners );

            Selector selector = resolver.resolveSelector( selectorString );
            ObjectMapHierarchicalModelImpl model = new ObjectMapHierarchicalModelImpl();
            traverser.traverse( instance, selector, model, context );
            return model.getUnderlyingModel();
        }
        catch (ParseSelectorException e)
        {
            Assert.fail( "Could not parse selector string " + selectorString );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail( "exception occurred" );
        }
        return null;
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


    public void setAliasSelectorResolver( AliasSelectorResolver aliasSelectorResolver )
    {
        resolver.getSelectorParser().setAliasSelectorResolver( aliasSelectorResolver );
    }

    public EntityConfigurationRegistry getEntityConfigurationRegistry()
    {
        return getCoreSelector().getEntityConfigurationRegistry();
    }
    
    public CoreSelector getCoreSelector()
    {
        return resolver.getBaseSelector();
    }
}
