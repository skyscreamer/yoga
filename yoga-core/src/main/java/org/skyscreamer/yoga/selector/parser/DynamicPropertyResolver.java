package org.skyscreamer.yoga.selector.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.skyscreamer.yoga.exceptions.ParseSelectorException;

/**
 * An implementation of {@link AliasSelectorResolver} that uses a Properties object accessed from a InputStream
 * (likely a resource or file) to translate the alias. The key of each property is the alias, and the value is the
 * fully-formed selector expression.
 */
public class DynamicPropertyResolver implements AliasSelectorResolver
{
    private int _reloadIntervalSeconds = 0;
    private long _nextReloadTime = 0;
    private Properties _properties = new Properties();
    private InputStream _propertyFile;

    public DynamicPropertyResolver()
    {
    }

    public DynamicPropertyResolver( InputStream propertyFile )
    {
        setPropertyFile( propertyFile );
    }

    public String resolveSelector( String aliasSelectorExpression ) throws ParseSelectorException
    {
        if ( _nextReloadTime == 0 || _reloadIntervalSeconds > 0 )
        {
            long milliseconds = System.currentTimeMillis();
            if ( milliseconds > _nextReloadTime )
            {
                try
                {
                    _properties.load( _propertyFile );
                    _nextReloadTime = milliseconds + (_reloadIntervalSeconds * 1000);
                }
                catch ( IOException e )
                {
                    throw new ParseSelectorException( "Could not load property file" );
                }
            }
        }

        String result = _properties.getProperty( aliasSelectorExpression );
        if ( result == null )
        {
            throw new ParseSelectorException( "No selector defined for " + aliasSelectorExpression );
        }

        return result;
    }

    /**
     * Setting this interval will instruct the resolver to reload the Properties object from the InputStream before
     * resolving the alias, if the specified number of seconds has elapsed since the last time the Properties object
     * was loaded. This can be useful to force the resolver to pick up changes made to the input file while the
     * application is running. Setting this value to 0 will prevent it from reloading the Properties object
     *
     * @param reloadIntervalSeconds interval in seconds, 0 is default
     */
    public void setReloadIntervalSeconds( int reloadIntervalSeconds )
    {
        _reloadIntervalSeconds = reloadIntervalSeconds;
    }

    /**
     * Defines the InputStream, such as a FileInputStream, that provides access to the Properties object
     *
     * @param propertyFile the input stream
     */
    public void setPropertyFile( InputStream propertyFile )
    {
        _propertyFile = propertyFile;
    }
}
