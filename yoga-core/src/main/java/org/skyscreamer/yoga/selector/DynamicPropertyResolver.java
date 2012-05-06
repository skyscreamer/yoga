package org.skyscreamer.yoga.selector;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA. User: corby
 */
public class DynamicPropertyResolver implements AliasSelectorResolver
{
    private int _reloadIntervalSeconds = 0;
    private long _nextReloadTime = 0;
    private Properties _properties = new Properties();
    private Resource _propertyFile;

    public String resolveSelector( String aliasSelectorExpression ) throws ParseSelectorException
    {
        if ( _nextReloadTime == 0 || _reloadIntervalSeconds > 0 )
        {
            long milliseconds = System.currentTimeMillis();
            if ( milliseconds > _nextReloadTime )
            {
                try
                {
                    _properties.load( _propertyFile.getInputStream() );
                    _nextReloadTime = milliseconds + (_reloadIntervalSeconds * 1000);
                }
                catch ( IOException e )
                {
                    throw new ParseSelectorException( "Could not load property file "
                            + _propertyFile.getFilename() );
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

    public void setReloadIntervalSeconds( int reloadIntervalSeconds )
    {
        _reloadIntervalSeconds = reloadIntervalSeconds;
    }

    public void setPropertyFile( Resource propertyFile )
    {
        _propertyFile = propertyFile;
    }
}
