package org.skyscreamer.yoga.metadata;

import java.util.Collection;

public interface MetaDataRegistry
{
    Collection<String> getTypes();

    Class<?> getTypeForName( String name );

    String getNameForType( Class<?> type );

    TypeMetaData getMetaData( String type, String suffix );

    TypeMetaData getMetaData( Class<?> type, String suffix );

    String getMetadataHref(Class<?> type, String suffix);

}
