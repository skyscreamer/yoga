package org.skyscreamer.yoga.uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.skyscreamer.yoga.util.ValueReader;

public class URICreator
{
    private static Pattern _uriTemplatePattern = Pattern.compile( "\\{[a-zA-Z0-9_]+\\}" );

    public static String getHref( String uriTemplate, ValueReader model, URIDecorator... decorators )
    {
        Matcher matcher = _uriTemplatePattern.matcher( uriTemplate );
        int start = 0;
        StringBuilder href = new StringBuilder();

        while ( matcher.find() )
        {
            String field = uriTemplate.substring( matcher.start() + 1, matcher.end() - 1 );
            href.append( uriTemplate.substring( start, matcher.start() ) ).append(
                    model.getValue( field ) );
            start = matcher.end();
        }
        href.append( uriTemplate.substring( start ) );
        for ( URIDecorator uriDecorator : decorators )
        {
            uriDecorator.decorate( href );
        }

        return href.toString();
    }
}
