package org.skyscreamer.yoga.metadata;

import java.util.Collection;

public interface MetaDataRegistry
{
    Collection<String> getTypes();

    TypeMetaData getMetaData( String type, String suffix );

    String getMetadataHref(Class<?> type, String suffix);
}
