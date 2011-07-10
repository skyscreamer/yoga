package org.skyscreamer.yoga.populator;

import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: corby
 */
public abstract class AbstractFieldPopulator<M> implements FieldPopulator<M>
{
    private Pattern _uriTemplatePattern = Pattern.compile( "\\{[a-zA-Z0-9_]+\\}" );

    public Map<String,Object> populateObjectFields( M model, Selector selector )
    {
        Map<String,Object> result = new HashMap<String, Object>();

        for ( String fieldName : getCoreFieldNames() )
        {
            addFieldToResult( fieldName, selector, model, result );
        }

        for ( String fieldName : selector.getFieldNames() )
        {
            addFieldToResult( fieldName, selector, model, result );
        }

        if ( getUriTemplate() != null )
        {
            addHypertextFieldToResult( getUriTemplate(), model, result );
        }
        return result;
    }

    private void addFieldToResult( String fieldName, Selector selector, M model, Map<String, Object> result )
    {
        if ( getModelFieldNames().contains( fieldName ) )
        {
            try
            {
                // TODO: Support read-only fields
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor( fieldName, model.getClass() );
                Method readMethod = propertyDescriptor.getReadMethod();
                result.put( fieldName, readMethod.invoke( model ) );
            }
            catch ( Exception e )
            {
                System.out.println( "Could not invoke getter for property " + fieldName + " on class " +
                    model.getClass().getName() );
            }
        }
        else
        {
            Object value = constructFieldValue( fieldName, model, selector );
            if ( value != null )
            {
                result.put( fieldName, value );
            }
        }
    }

    private void addHypertextFieldToResult( String uriTemplate, M model, Map<String,Object> result )
    {
        String href = uriTemplate;
        String resourceIdField = parseResourceIdField( uriTemplate );
        try
        {
            if ( !resourceIdField.equals( "" ) )
            {
                // TODO: Support read-only fields
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor( resourceIdField, model.getClass() );
                Method readMethod = propertyDescriptor.getReadMethod();
                href = populateResourceIdField( uriTemplate, readMethod.invoke( model ) );
            }
        }
        catch ( Exception e )
        {
            System.out.println( "Could not invoke getter for property " + uriTemplate + " on class " +
                model.getClass().getName() );
        }
        result.put( SelectorParser.HREF, href );
    }

    private String parseResourceIdField( String uriTemplate )
    {
        Matcher matcher = _uriTemplatePattern.matcher( uriTemplate );

        String result = "";
        if ( matcher.find() )
        {
            result = uriTemplate.substring( matcher.start() + 1, matcher.end() -1 );
        }
        return result;
    }

    private String populateResourceIdField( String uriTemplate, Object property )
    {
        Matcher matcher = _uriTemplatePattern.matcher( uriTemplate );

        String result = uriTemplate;
        if ( matcher.find() )
        {
            result = uriTemplate.substring( 0, matcher.start() ) + property.toString() +
                uriTemplate.substring( matcher.end(), uriTemplate.length() );
        }
        return result;
    }

    protected abstract Object constructFieldValue( String fieldName, M model, Selector selector );

    protected abstract Collection<String> getCoreFieldNames();

    protected abstract Collection<String> getModelFieldNames();

    protected String getUriTemplate()
    {
        return null;
    }

    public List<Map<String,Object>> populateListFields( Collection<M> models, Selector selector )
    {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        for ( M model : models )
        {
            result.add( populateObjectFields( model, selector ) );
        }
        return result;
    }
}
